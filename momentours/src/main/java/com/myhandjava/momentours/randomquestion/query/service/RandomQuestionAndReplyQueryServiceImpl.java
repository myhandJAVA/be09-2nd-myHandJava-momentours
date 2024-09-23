package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.couple.query.service.CoupleService;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomQuestion;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionAndReplyDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.query.repository.RandomQuestionMapper;
import com.myhandjava.momentours.randomquestion.query.repository.RandomReplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("RandomQuesQueryService")
@Slf4j
public class RandomQuestionAndReplyQueryServiceImpl implements RandomQuestionAndReplyQueryService {

    private final RandomQuestionMapper randomQuestionMapper;
    private final RandomReplyMapper randomReplyMapper;
    private final CoupleService coupleService;

    @Autowired
    public RandomQuestionAndReplyQueryServiceImpl(RandomQuestionMapper randomQuestionMapper,
                                                  RandomReplyMapper randomReplyMapper,
                                                  CoupleService coupleService) {
        this.randomQuestionMapper = randomQuestionMapper;
        this.randomReplyMapper = randomReplyMapper;
        this.coupleService = coupleService;
    }

    @Override
    public RandomQuestionDTO findCurrentRandomQuestionByCoupleNo(int coupleNo) {
        RandomQuestion randomQuestion = randomQuestionMapper.findCurrentRandomQuestionByCoupleNo(coupleNo);
        RandomQuestionDTO randomQuestionDTO = new RandomQuestionDTO(
                randomQuestion.getRandQuesNo(), randomQuestion.getRandQuesCreateDate(), randomQuestion.getRandQuesContent(),
                randomQuestion.getRandQuesReply(), randomQuestion.getRandQuesIsDeleted(), randomQuestion.getRandQuesCoupleNo()
        );
        return randomQuestionDTO;
    }

    @Override
    public List<RandomQuestionDTO> findAllRandomQuestionByCoupleNo(int coupleNo) {
        List<RandomQuestion> randomQuestions = randomQuestionMapper.findAllRandomQuestionByCoupleNo(coupleNo);
        List<RandomQuestionDTO> randomQuestionDTOList = randomQuestions.stream().map(randomQuestion ->
                new RandomQuestionDTO(randomQuestion.getRandQuesNo(), randomQuestion.getRandQuesCreateDate(),
                        randomQuestion.getRandQuesContent(), randomQuestion.getRandQuesReply(),
                        randomQuestion.getRandQuesIsDeleted(), randomQuestion.getRandQuesCoupleNo()
                        )).collect(Collectors.toList());
        return randomQuestionDTOList;
    }

    @Override
    public RandomQuestionDTO findRandomQuestionByDate(Map<String, Object> map) {
        RandomQuestion randomQuestion = randomQuestionMapper.findRandomQuestionByDate(map);
        RandomQuestionDTO randomQuestionDTO = new RandomQuestionDTO(
                randomQuestion.getRandQuesNo(), randomQuestion.getRandQuesCreateDate(), randomQuestion.getRandQuesContent(),
                randomQuestion.getRandQuesReply(), randomQuestion.getRandQuesIsDeleted(), randomQuestion.getRandQuesCoupleNo()
        );
        return randomQuestionDTO;
    }

    @Override
    public List<RandomQuestionDTO> findRandomQuestionByKeyword(Map<String, Object> map) {
        List<RandomQuestion> randomQuestions = randomQuestionMapper.findRandomQuestionByKeyword(map);
        List<RandomQuestionDTO> randomQuestionDTOList = randomQuestions.stream().map(randomQuestion ->
                new RandomQuestionDTO(randomQuestion.getRandQuesNo(), randomQuestion.getRandQuesCreateDate(),
                        randomQuestion.getRandQuesContent(), randomQuestion.getRandQuesReply(),
                        randomQuestion.getRandQuesIsDeleted(), randomQuestion.getRandQuesCoupleNo()
                )).collect(Collectors.toList());
        return randomQuestionDTOList;
    }

    @Override
    public RandomReplyDTO findRandomReplyByQuestionNoAndUserNo(Map<String, Object> map) {
        RandomReply randomReply;
        RandomReplyDTO randomReplyDTO;
        try {
            randomReply = randomReplyMapper.findRandomReplyByQuestionNoAndUserNo(map);
            if(randomReply != null) {
                randomReplyDTO = new RandomReplyDTO(
                        randomReply.getRandomReplyNo(), randomReply.getRandomReplyContent(), randomReply.getRandomReplyUserNo(),
                        randomReply.getRandomQuestionNo(), randomReply.getRandomReplyIsDeleted(), randomReply.getRandomCoupleNo()
                );
            } else {
                randomReplyDTO = createEmptyReplyDTO();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return randomReplyDTO;
    }

    private RandomReplyDTO createHiddenReplyDTO() {
        return new RandomReplyDTO(0, "조회를 위해선 답변을 작성해주세요",
                0, 0, 0, 0);
    }

    private RandomReplyDTO createEmptyReplyDTO() {
        return new RandomReplyDTO(0, "텅", 0,
                0, 0, 0);
    }

    @Override
    public List<RandomQuestionAndReplyDTO> findAllRandomQuestionAndRepliesByUserNoAndCoupleNo(int coupleNo , int userNo, int partnerNo) {
        List<RandomQuestionDTO> randomQuestionList = findAllRandomQuestionByCoupleNo(coupleNo);
        List<RandomQuestionAndReplyDTO> result = new ArrayList<>();
        for (RandomQuestionDTO question : randomQuestionList) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userNo", userNo);
            userMap.put("randomQuestionNo", question.getRandQuesNo());
            Map<String, Object> partnerMap = new HashMap<>();
            partnerMap.put("userNo", partnerNo);
            partnerMap.put("randomQuestionNo", question.getRandQuesNo());
            RandomReplyDTO userReplyDTO = findRandomReplyByQuestionNoAndUserNo(userMap);
            RandomReplyDTO partnerReplyDTO = findRandomReplyByQuestionNoAndUserNo(partnerMap);
            //둘 다 답장을 안 했으면 볼 수 있다 "텅" "텅"
            if(userReplyDTO.getRandomReplyContent().equals("텅")
                    && partnerReplyDTO.getRandomReplyContent().equals("텅")) {
                RandomQuestionAndReplyDTO noAnswer = new RandomQuestionAndReplyDTO(
                        question,
                        userReplyDTO,
                        partnerReplyDTO,
                        true
                );
                result.add(noAnswer);
                continue;
            }
            boolean canViewPartnerReply = !userReplyDTO.getRandomReplyContent().equals("텅");
            RandomQuestionAndReplyDTO dto = new RandomQuestionAndReplyDTO(
                    question,
                    userReplyDTO,
                    canViewPartnerReply ? partnerReplyDTO : createHiddenReplyDTO(),
                    true
            );
            result.add(dto);
        }
        return result;
    }
}
