package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RandomQuestionAndReplyServiceImplTests {

    @Autowired
    RandomQuestionAndReplyServiceImpl randomQuestionAndReplyService;


    @DisplayName("최신 랜덤 질문 조회 혹은 생성")
    @Test
    void getOrGenerateRandomQuestion() {
        int coupleNo = 1;
        RandomQuestionDTO randomQuestion = randomQuestionAndReplyService.getCurrentRandomQuestion(coupleNo);

        assertNotNull(randomQuestion);
    }

    @DisplayName("랜덤 질문 답변 생성")
    @Test
    void registReply() {
        RandomReplyDTO reply = new RandomReplyDTO();
        reply.setRandomReplyContent("내 뇌를 gpt로 대체할래!");
        reply.setRandomReplyUserNo(1);
        reply.setRandomReplyIsDeleted(0);
        reply.setRandomQuestionNo(8);
        reply.setRandomCoupleNo(1);

        assertDoesNotThrow(() -> randomQuestionAndReplyService.registRandomReply(reply));
    }

    @DisplayName("랜덤 질문 답변 삭제(soft delete)")
    @Test
    void deleteReply() {

        assertDoesNotThrow(() -> randomQuestionAndReplyService.removeRandomReply(1));
    }

    @DisplayName("랜덤 질문 답변 수정")
    @Test
    void updateReply() {
        RandomReplyDTO reply = new RandomReplyDTO();
        int replyNo = 1;
        reply.setRandomReplyUserNo(1);
        reply.setRandomReplyIsDeleted(0);
        reply.setRandomQuestionNo(8);
        reply.setRandomCoupleNo(1);
        reply.setRandomReplyContent("집에... 집에 가고 싶어요!!!");

        assertDoesNotThrow(() -> randomQuestionAndReplyService.updateRandomReply(replyNo, reply));
    }
}