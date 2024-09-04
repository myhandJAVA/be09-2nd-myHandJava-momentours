package com.myhandjava.momentours.randomquestion.command.application.service;

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
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service("randomQAndRCommandService")
@Slf4j
public class RandomQuestionAndReplyServiceImpl implements RandomQuestionAndReplyService {

    private final RandomReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    private final CoupleServiceImpl coupleService;
    private final OpenAIServiceImpl openAIService;
    private final RandomQuestionRepository questionRepository;

    @Autowired
    public RandomQuestionAndReplyServiceImpl(ModelMapper modelMapper, RandomQuestionRepository questionRepository,
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
    public void removeRandomReply(int randomreplyno) {
        RandomReply randomReply =
                replyRepository.findByRandomReplyNo(randomreplyno)
                        .orElseThrow(() -> new EntityNotFoundException("질문 답변이 존재하지 않습니다."));

        if (randomReply != null) {
            randomReply.setRandomReplyIsDeleted(1);
            replyRepository.save(randomReply);
        }
    }

    @Override
    @Transactional
    public void updateRandomReply(int replyNo, RandomReplyDTO replyDTO) {
        RandomReply randomReply =
                replyRepository.findByRandomReplyNo(replyNo)
                        .orElseThrow(() -> new EntityNotFoundException("질문 답변이 존재하지 않습니다."));
        if (randomReply != null) {
            randomReply.setRandomReplyContent(replyDTO.getRandomReplyContent());
            log.info("바뀐 답변 내용 확인: {}", randomReply);
            replyRepository.save(randomReply);
        }
    }

    @Override
    @Transactional
    public void registRandomReply(RandomReplyDTO randomReplyDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RandomReply randomReply =
                modelMapper.map(randomReplyDTO, RandomReply.class);
        log.info("replyDTO -> Entity로 전환: {}" + randomReply);

        replyRepository.save(randomReply);
    }

    @Override
    public RandomQuestionDTO getCurrentRandomQuestion(int coupleNo) {
        // 페이지 크기를 1로 설정하여 최신의 질문 1개만 조회
        Pageable pageable = PageRequest.of(0, 1);
        List<RandomQuestion> currentQuestion =
                questionRepository.findLatestUnansweredQuestion(coupleNo, pageable);

        // 질문이 없다면 새 질문 생성
        if (currentQuestion.isEmpty()) {
            Map<String, Object> coupleInfo = coupleService.getCoupleInfo(coupleNo);
            String newQuestion = openAIService.generateQuestionForCouple(coupleInfo);

            log.info("새로운 질문 생성:{}", newQuestion);
            RandomQuestionDTO newQuestionDTO = saveNewQuestion(coupleNo, newQuestion);
            return newQuestionDTO;
        }

        // 아니면 리스트에서 첫 번째 질문을 가져옴
        RandomQuestion rq = currentQuestion.get(0);
        // DTO로 변환
        RandomQuestionDTO randomQuestion = modelMapper.map(rq, RandomQuestionDTO.class);
        log.info("답변하지 않은 질문 반환:{}", randomQuestion);
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
}
