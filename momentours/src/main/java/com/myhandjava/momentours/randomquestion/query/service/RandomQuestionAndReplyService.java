package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionAndReplyDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomReplyDTO;

import java.util.List;
import java.util.Map;

public interface RandomQuestionAndReplyService {

    RandomQuestionDTO findCurrentRandomQuestionByCoupleNo(int coupleNo);

    List<RandomQuestionDTO> findAllRandomQuestionByCoupleNo(int coupleNo);

    RandomQuestionDTO findRandomQuestionByDate(Map<String, Object> map);

    List<RandomQuestionDTO> findRandomQuestionByKeyword(Map<String, Object> map);

    RandomReplyDTO findRandomReplyByQuestionNoAndUserNo(Map<String, Object> map);

    List<RandomQuestionAndReplyDTO> findAllRandomQuestionAndRepliesByUserNoAndCoupleNo(int coupleNo, int userNo, int parentNo);
}
