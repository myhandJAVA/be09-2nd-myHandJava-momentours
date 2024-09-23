package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomQuestionAndReplyQueryServiceImplTests {

    @Autowired
    RandomQuestionAndReplyCommandServiceImpl randomQuestionAndReplyService;


    @DisplayName("최신 랜덤 질문 조회 혹은 생성")
    @Test
    void getOrGenerateRandomQuestion() {
        //given
        int coupleNo = 1;
        RandomQuestionDTO randomQuestion = randomQuestionAndReplyService.findRandomQuestion(coupleNo);

        assertNotNull(randomQuestion);
    }

    @DisplayName("랜덤 질문 답변 생성")
    @Test
    void registReply() {
        RandomReplyDTO reply = new RandomReplyDTO();
        reply.setRandomReplyContent("오늘은 영화관을 갈래!");
        reply.setRandomReplyUserNo(1);
        reply.setRandomReplyIsDeleted(0);
        reply.setRandomQuestionNo(8);
        reply.setRandomCoupleNo(1);

        assertDoesNotThrow(() -> randomQuestionAndReplyService.registRandomReply(8, reply));
    }

    @DisplayName("랜덤 질문 답변 삭제(soft delete)")
    @Test
    void deleteReply() {
        //given
        int userNo = 1;
        int replyNo = 1;
        //when
        //then
        assertDoesNotThrow(() -> randomQuestionAndReplyService.removeRandomReply(userNo, replyNo));
    }

    @DisplayName("랜덤 질문 답변 수정")
    @Test
    void updateReply() {
        RandomReplyDTO reply = new RandomReplyDTO();
        //given
        int replyNo = 1;
        int userNo = 1;
        // when
        reply.setRandomReplyUserNo(1);
        reply.setRandomReplyIsDeleted(0);
        reply.setRandomQuestionNo(8);
        reply.setRandomCoupleNo(1);
        reply.setRandomReplyContent("랜덤질문 답변 합니다.");
        //then
        assertDoesNotThrow(() -> randomQuestionAndReplyService.modifyRandomReply(replyNo, reply));
    }
}