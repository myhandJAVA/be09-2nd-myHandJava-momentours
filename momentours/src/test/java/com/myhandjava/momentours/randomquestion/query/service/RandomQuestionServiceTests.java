package com.myhandjava.momentours.randomquestion.query.service;

import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomReplyDTO;
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
    private RandomQuestionAndReplyQueryServiceImpl randomQuestionService;

    @DisplayName("커플 번호로 가장 최신 랜덤질문 조회")
    @Test
    public void getRandomQuestion() {
        int coupleNo = 1;
        RandomQuestionDTO randomQuestionDTO =
                randomQuestionService.findCurrentRandomQuestionByCoupleNo(coupleNo);

        assertNotNull(randomQuestionDTO);
    }

    @DisplayName("커플 번호로 모든 랜덤질문 최신순으로 조회")
    @Test
    public void getAllRandomQuestion() {
        int coupleNo = 1;
        List<RandomQuestionDTO> randomQuestionDTOs =
                randomQuestionService.findAllRandomQuestionByCoupleNo(coupleNo);

        assertNotNull(randomQuestionDTOs);
    }

    @DisplayName("날짜와 커플 번호로 랜덤질문 검색")
    @Test
    public void getRandomQuestionByCoupleNoAndDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("coupleNo", 1);
        map.put("selectedDate", "2024-08-29");
        RandomQuestionDTO randomQuestionDTO =
                randomQuestionService.findRandomQuestionByDate(map);

        assertNotNull(randomQuestionDTO);
    }

    @DisplayName("커플 번호와 키워드로 랜덤질문 검색")
    @Test
    public void getRandomQuestionByCoupleNoAndKeyword() {
        Map<String, Object> map = new HashMap<>();
        map.put("coupleNo", 1);
        map.put("keyword", "요?");

        List<RandomQuestionDTO> randomQuestionDTOs =
                randomQuestionService.findRandomQuestionByKeyword(map);

        assertNotNull(randomQuestionDTOs);
    }

    @DisplayName("회원번호와 질문번호로 랜덤질문 답변 조회")
    @Test
    public void getRandomReply() {
        int userNo = 2;
        int randomQuestionNo = 1;
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("randomQuestionNo", randomQuestionNo);

        RandomReplyDTO randomReplyDTO =
                randomQuestionService.findRandomReplyByQuestionNoAndUserNo(map);

        assertNotNull(randomReplyDTO);
    }
}