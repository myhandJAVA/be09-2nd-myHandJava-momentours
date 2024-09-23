package com.myhandjava.momentours.randomquestion.command.application.service;

import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;

public interface RandomQuestionAndReplyCommandService {
    void removeRandomReply(int replyNo, int userNo);
    void modifyRandomReply(int replyNo, RandomReplyDTO modifyContent);
    void registRandomReply(int questionNo, RandomReplyDTO randomReplyDTO) throws Exception;
    RandomQuestionDTO findRandomQuestion(int coupleNo);
    RandomQuestionDTO saveNewQuestion(int coupleNo, String content) throws Exception;
    void removeAllRandomQuestionAndReply(int coupleNo);
}
