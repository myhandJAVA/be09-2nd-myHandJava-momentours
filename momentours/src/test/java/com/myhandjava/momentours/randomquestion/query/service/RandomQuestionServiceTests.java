package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.config.MybatisConfiguration;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomQuestionServiceTests {

    @Autowired
    private RandomQuestionService randomQuestionService;

    @DisplayName("회원 번호로 가장 최신 랜덤질문 조회")
    @Test
    public void getRandomQuestion() {
        int userNo = 1;
        RandomQuestionDTO randomQuestionDTO =
                randomQuestionService.findCurrentRandomQuestionByMemberNo(userNo);

        assertNotNull(randomQuestionDTO);
    }

    @DisplayName("회원 번호로 모든 랜덤질문 최신순으로 조회")
    @Test
    public void getAllRandomQuestion() {
        int userNo = 1;
        List<RandomQuestionDTO> randomQuestionDTOs =
                randomQuestionService.findAllRandomQuestionByMemberNo(userNo);

        assertNotNull(randomQuestionDTOs);
    }

    @DisplayName("날짜와 회원 번호로 랜덤질문 검색")
    @Test
    public void getRandomQuestionByMemberNoAndDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", 1);
        map.put("selectedDate", "2024-08-29");
        RandomQuestionDTO randomQuestionDTO =
                randomQuestionService.findRandomQuestionByDate(map);

        assertNotNull(randomQuestionDTO);
    }

    @DisplayName("회원 번호와 키워드로 랜덤질문 검색")
    @Test
    public void getRandomQuestionByMemberNoAndKeyword() {
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", 1);
        map.put("keyword", "요?");
        List<RandomQuestionDTO> randomQuestionDTOs =
                randomQuestionService.findRandomQuestionByKeyword(map);

        assertNotNull(randomQuestionDTOs);
    }
}