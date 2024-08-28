package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;

public interface RandomQuestionService {

    RandomQuestionDTO findCurrentRandomQuestionByMemberNo(int userNo);
}
