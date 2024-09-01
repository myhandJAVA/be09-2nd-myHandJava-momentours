package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionAndReplyDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomReplyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface RandomQuestionService {

    RandomQuestionDTO findCurrentRandomQuestionByMemberNo(int userNo);

    List<RandomQuestionDTO> findAllRandomQuestionByMemberNo(int userNo);

    RandomQuestionDTO findRandomQuestionByDate(Map<String, Object> map);

    List<RandomQuestionDTO> findRandomQuestionByKeyword(Map<String, Object> map);

    RandomReplyDTO findRandomReplyByQuestionNoAndUserNo(Map<String, Object> map);

    RandomQuestionAndReplyDTO findCurrentRandomQuestionWithReplies(int userNo);
}
