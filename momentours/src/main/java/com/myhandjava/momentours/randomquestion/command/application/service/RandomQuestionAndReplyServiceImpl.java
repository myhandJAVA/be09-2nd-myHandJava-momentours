package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import com.myhandjava.momentours.randomquestion.command.domain.repository.RandomQuestionRepository;
import com.myhandjava.momentours.randomquestion.command.domain.repository.RandomReplyRepository;
import com.myhandjava.momentours.randomquestion.command.domain.vo.RegistRequestReplyVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("randomQAndRCommandService")
@Slf4j
public class RandomQuestionAndReplyServiceImpl implements RandomQuestionAndReplyService {

    private final RandomQuestionRepository questionRepository;
    private final RandomReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionAndReplyServiceImpl(ModelMapper modelMapper, RandomQuestionRepository randomQuestionRepository, RandomReplyRepository replyRepository) {
        this.modelMapper = modelMapper;
        this.questionRepository = randomQuestionRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public void createRandomQuestion(int userNo) {

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
}
