package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.domain.Repository.RandomQuestionRepository;
import com.myhandjava.momentours.randomquestion.command.domain.Repository.RandomReplyRepository;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "randomQuestionCommandService")
@Slf4j
public class RandomQuestionServiceImpl implements RandomQuestionService {

    private final RandomQuestionRepository randomQuestionRepository;
    private final RandomReplyRepository randomReplyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionServiceImpl(ModelMapper modelMapper, RandomQuestionRepository randomQuestionRepository, RandomReplyRepository replyRepository) {
        this.modelMapper = modelMapper;
        this.randomQuestionRepository = randomQuestionRepository;
        this.randomReplyRepository = replyRepository;
    }

    @Override
    public void createRandomQuestion(int userNo) {

    }

    @Override
    public void removeRandomReply(int randomreplyno, int randomreplyuserno) {
        RandomReply randomReply =
                randomReplyRepository.findByRandomQuestionNoAndRandomReplyUserNo(randomreplyno, randomreplyuserno);

        if (randomReply != null) {
            randomReply.setRandomreplyisdeleted(1);
            randomReplyRepository.save(randomReply);
        } else {
            throw new EntityNotFoundException("답변이 존재하지 않습니다.");
        }
    }
}
