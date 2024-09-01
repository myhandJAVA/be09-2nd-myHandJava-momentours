package com.myhandjava.momentours.randomquestion.command.application.service;

public interface RandomQuestionService {
    void createRandomQuestion(int userNo);
    void removeRandomReply(int userNo, int questionNo);
}
