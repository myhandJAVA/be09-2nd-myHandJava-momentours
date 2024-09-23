package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.query.service.CoupleServiceImpl;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import com.myhandjava.momentours.randomquestion.command.domain.repository.RandomQuestionRepository;
import com.myhandjava.momentours.randomquestion.command.domain.repository.RandomReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service("randomQuesCommandService")
@Slf4j
public class RandomQuestionAndReplyCommandServiceImpl implements RandomQuestionAndReplyCommandService {

    private final RandomReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final CoupleServiceImpl coupleService;
    private final OpenAIServiceImpl openAIService;
    private final RandomQuestionRepository questionRepository;

    @Autowired
    public RandomQuestionAndReplyCommandServiceImpl(ModelMapper modelMapper, RandomQuestionRepository questionRepository,
                                                    RandomReplyRepository replyRepository, CoupleServiceImpl coupleService,
                                                    OpenAIServiceImpl openAIService) {
        this.modelMapper = modelMapper;
        this.replyRepository = replyRepository;
        this.coupleService = coupleService;
        this.openAIService = openAIService;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void removeRandomReply(int replyNo, int userNo) {
        RandomReply randomReply =
                replyRepository.findByRandomReplyNoAndRandomReplyUserNo(replyNo, userNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION_REPLY));

        if (randomReply != null) {
            randomReply.setRandomReplyIsDeleted(1);
            replyRepository.save(randomReply);
        }
    }

    @Override
    @Transactional
    public void modifyRandomReply(int replyNo, RandomReplyDTO replyDTO) {
        if (replyDTO.getRandomReplyContent() == null)
            throw new CommonException(HttpStatusCode.BAD_REQUEST_RANDOMQUESTION_REPLY);
        RandomReply randomReply =
                replyRepository.findByRandomReplyNoAndRandomReplyUserNo(replyDTO.getRandomReplyUserNo(), replyNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION_REPLY));
        if (randomReply != null) {
            randomReply.setRandomReplyContent(replyDTO.getRandomReplyContent());
            replyRepository.save(randomReply);
        }
    }

    @Override
    @Transactional
    public void registRandomReply(int questionNo, RandomReplyDTO randomReplyDTO) {
        int userNo = randomReplyDTO.getRandomReplyUserNo();
        int coupleNo = randomReplyDTO.getRandomCoupleNo();
        RandomQuestion randomQuestion =
                questionRepository.findRandomQuestionByRandQuesNo(questionNo).
                        orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION));
        List<RandomReply> replies =
                replyRepository.findRandomReplyByRandomQuestionNo(questionNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION_REPLY));
        if (replies.size() < 1) {
            RandomReply randomReply = new RandomReply();
            randomReply.setRandomReplyContent(randomReplyDTO.getRandomReplyContent());
            randomReply.setRandomReplyUserNo(userNo);
            randomReply.setRandomQuestionNo(questionNo);
            randomReply.setRandomCoupleNo(coupleNo);
            replyRepository.save(randomReply);
        } else if (replies.size() == 1) {
            RandomReply randomReply = new RandomReply();
            randomReply.setRandomReplyContent(randomReplyDTO.getRandomReplyContent());
            randomReply.setRandomReplyUserNo(userNo);
            randomReply.setRandomQuestionNo(questionNo);
            randomReply.setRandomCoupleNo(coupleNo);
            replyRepository.save(randomReply);
            randomQuestion.setRandQuesReply(1);
            questionRepository.save(randomQuestion);
        } else {
            throw new CommonException(HttpStatusCode.CONFLICT_RANDOMQUESTION_REPLY);
        }
    }

    @Override
    public RandomQuestionDTO findRandomQuestion(int coupleNo) {
        RandomQuestion currentQ = questionRepository.findLatestUnansweredQuestion(coupleNo).orElse(null);
        // 질문이 없다면 새 질문 생성
        if (currentQ == null) {
            Map<String, Object> coupleInfo = coupleService.getCoupleInfo(coupleNo);
            String newQuestion = openAIService.generateQuestionForCouple(coupleInfo);
            RandomQuestionDTO newQuestionDTO = saveNewQuestion(coupleNo, newQuestion);
            return newQuestionDTO;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Today = LocalDateTime.now().format(formatter);
        String questionCreatedDate = currentQ.getRandQuesCreateDate().format(formatter);
        // 날짜와 답변 여부를 따져서 새 질문 생성
        if (currentQ.getRandQuesReply() == 1 && !Today.equals(questionCreatedDate)) {
            Map<String, Object> coupleInfo = coupleService.getCoupleInfo(coupleNo);
            String newQuestion = openAIService.generateQuestionForCouple(coupleInfo);
            RandomQuestionDTO newQuestionDTO = saveNewQuestion(coupleNo, newQuestion);
            return newQuestionDTO;
        }
        RandomQuestionDTO randomQuestion = modelMapper.map(currentQ, RandomQuestionDTO.class);
        return randomQuestion;
    }


    @Override
    public RandomQuestionDTO saveNewQuestion(int coupleNo, String content) {
        RandomQuestionDTO newQuestionDTO = new RandomQuestionDTO();

        RandomQuestion randomQuestion = new RandomQuestion();
        randomQuestion.setRandQuesReply(0);
        randomQuestion.setRandQuesCreateDate(LocalDateTime.now());
        randomQuestion.setRandQuesIsDeleted(0);
        randomQuestion.setRandQuesContent(content);
        randomQuestion.setRandQuesCoupleNo(coupleNo);

        newQuestionDTO.setRandQuesIsDeleted(randomQuestion.getRandQuesIsDeleted());
        newQuestionDTO.setRandQuesCreateDate(randomQuestion.getRandQuesCreateDate());
        newQuestionDTO.setRandQuesCoupleNo(coupleNo);
        newQuestionDTO.setRandQuesContent(content);
        newQuestionDTO.setRandQuesReply(randomQuestion.getRandQuesReply());

        questionRepository.save(randomQuestion);
        return newQuestionDTO;
    }

    @Override
    public void removeAllRandomQuestionAndReply(int coupleNo) {
        List<RandomQuestion> allQuestions =
                questionRepository.findAllByRandQuesCoupleNo(coupleNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION));
        List<RandomReply> allReplies =
                replyRepository.findAllByRandomCoupleNo(coupleNo)
                        .orElseThrow(() -> new CommonException(HttpStatusCode.NOT_FOUND_RANDOMQUESTION_REPLY));
        for(RandomQuestion q : allQuestions) {
            questionRepository.delete(q);
        }
        for(RandomReply r : allReplies) {
            replyRepository.delete(r);
        }
    }
}
