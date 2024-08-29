package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.repository.RandomQuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<RandomQuestionDTO> findAllRandomQuestionByMemberNo(int userNo) {
        List<RandomQuestion> randomQuestions = randomQuestionMapper.findAllRandomQuestionByMemberNo(userNo);

        List<RandomQuestionDTO> randomQuestionDTOList = randomQuestions.stream().map(randomQuestion ->
                new RandomQuestionDTO(randomQuestion.getRandquesno(), randomQuestion.getRandquescreatedate(),
                        randomQuestion.getRandquescontent(), randomQuestion.getRandquesreply(),
                        randomQuestion.getRandquesisdeleted(), randomQuestion.getRandquescoupleno()
                        )).collect(Collectors.toList());

        log.info("반환된 값: {}", randomQuestionDTOList);
        return randomQuestionDTOList;
    }

    @Override
    public RandomQuestionDTO findRandomQuestionByDate(Map<String, Object> map) {
        RandomQuestion randomQuestion = randomQuestionMapper.findRandomQuestionByDate(map);
        RandomQuestionDTO randomQuestionDTO = new RandomQuestionDTO(
                randomQuestion.getRandquesno(), randomQuestion.getRandquescreatedate(), randomQuestion.getRandquescontent(),
                randomQuestion.getRandquesreply(), randomQuestion.getRandquesisdeleted(), randomQuestion.getRandquescoupleno()
        );

        log.info("반환된 값: {}", randomQuestionDTO);
        return randomQuestionDTO;
    }

    @Override
    public List<RandomQuestionDTO> findRandomQuestionByKeyword(Map<String, Object> map) {
        List<RandomQuestion> randomQuestions = randomQuestionMapper.findRandomQuestionByKeyword(map);

        List<RandomQuestionDTO> randomQuestionDTOList = randomQuestions.stream().map(randomQuestion ->
                new RandomQuestionDTO(randomQuestion.getRandquesno(), randomQuestion.getRandquescreatedate(),
                        randomQuestion.getRandquescontent(), randomQuestion.getRandquesreply(),
                        randomQuestion.getRandquesisdeleted(), randomQuestion.getRandquescoupleno()
                )).collect(Collectors.toList());

        log.info("반환된 값: {}", randomQuestionDTOList);
        return randomQuestionDTOList;
    }
}
