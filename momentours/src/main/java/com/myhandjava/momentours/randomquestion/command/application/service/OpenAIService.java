package com.myhandjava.momentours.randomquestion.command.application.service;

import java.util.Map;

public interface OpenAIService {
    String generateQuestionForCouple(Map<String, Object> coupleInfo);
}
