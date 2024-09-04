package com.myhandjava.momentours.randomquestion.command.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OpenAIServiceTests {

    @Autowired
    private OpenAIServiceImpl openAIService;

    @DisplayName("커플별 고유 랜덤질문 생성")
    @Test
    void generateRandomQuestion() {
        Map<String, Object> coupleInfo = new HashMap<>();
        List<Integer> age = new ArrayList<>(); age.add(20); age.add(20);
        coupleInfo.put("age", age);
//        List<String>
    }
}