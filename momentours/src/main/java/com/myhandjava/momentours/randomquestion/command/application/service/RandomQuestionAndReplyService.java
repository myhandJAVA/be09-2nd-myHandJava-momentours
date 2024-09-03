package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;

public interface RandomQuestionAndReplyService {
    void createRandomQuestion(int userNo);
    void removeRandomReply(int replyNo);
    void updateRandomReply(int replyNo, RandomReplyDTO modifyContent);
    void registRandomReply(RandomReplyDTO randomReplyDTO) throws Exception;
}
