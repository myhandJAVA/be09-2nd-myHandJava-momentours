package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.repository.RandomQuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RandomQuestionServiceImpl implements RandomQuestionService {

    private final RandomQuestionMapper randomQuestionMapper;

    @Autowired
    public RandomQuestionServiceImpl(RandomQuestionMapper randomQuestionMapper) {
        this.randomQuestionMapper = randomQuestionMapper;
    }

    @Override
    public RandomQuestionDTO findCurrentRandomQuestionByMemberNo(int userNo) {
        RandomQuestion randomQuestion = randomQuestionMapper.findCurrentRandomQuestionByMemberNo(userNo);
        RandomQuestionDTO randomQuestionDTO = new RandomQuestionDTO(
                randomQuestion.getRandquesno(), randomQuestion.getRandquescreatedate(), randomQuestion.getRandquescontent(),
                randomQuestion.getRandquesreply(), randomQuestion.getRandquesisdeleted(), randomQuestion.getRandquescoupleno()
        );
        log.info("반환된 값: {}", randomQuestionDTO);
        return randomQuestionDTO;
    }
}
