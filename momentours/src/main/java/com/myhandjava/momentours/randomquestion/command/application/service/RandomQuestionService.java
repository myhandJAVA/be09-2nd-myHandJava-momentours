package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;

public interface RandomQuestionService {
    void createRandomQuestion(int userNo);
    void removeRandomReply(int replyNo);
    void updateRandomReply(int replyNo, RandomReplyDTO modifyContent);
}
