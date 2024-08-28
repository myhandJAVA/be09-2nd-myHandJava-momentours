package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.config.MybatisConfiguration;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomQuestionServiceTests {

    @Autowired
    private RandomQuestionService randomQuestionService;

    @DisplayName("회원 번호로 가장 최신 랜덤질문 조회")
    @Test
    public void getRandomQuestion() {
        int userNo = 1;
        RandomQuestionDTO randomQuestionDTO = randomQuestionService.findCurrentRandomQuestionByMemberNo(userNo);

        assertNotNull(randomQuestionDTO);
    }
}