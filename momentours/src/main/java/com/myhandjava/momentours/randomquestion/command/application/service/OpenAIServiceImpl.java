package com.myhandjava.momentours.randomquestion.command.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(value = "OpenAIService")
@Slf4j
public class OpenAIServiceImpl implements OpenAIService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public OpenAIServiceImpl(RestTemplate restTemplate,
                             @Value("${openai.api.key}") String apiKey,
                             @Value("${openai.api.url}") String apiUrl,
                             ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateQuestionForCouple(Map<String, Object> coupleInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String prompt = buildPrompt(coupleInfo);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", List.of(
                Map.of(
                        "role", "system",
                        "content", "당신은 창의적이고 통찰력 있는 연애 코치이자 베스트셀러 연애 칼럼니스트입니다. " +
                                "당신의 질문들은 항상 소셜 미디어에서 화제가 되며, 많은 커플들이 당신의 조언을 듣기 위해 줄을 섭니다. " +
                                "당신은 재치 있고, 때로는 도발적이면서도 항상 사려 깊은 방식으로 " +
                                "커플들의 관계를 더 깊고 흥미롭게 만드는 talent를 가지고 있습니다."
                ),
                Map.of(
                        "role", "user",
                        "content", prompt
                )
        ));
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        int retries = 3;
        while (retries > 0) {
            try {
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
                String responseBody = response.getBody();

                // JSON 응답에서 질문 내용만 추출
                String questionContent = extractQuestionContent(responseBody);
                // " " 없애기 위한 subString
                questionContent = questionContent.replace("\"","");
                questionContent = questionContent.replace("\\","");

                return questionContent;
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException.TooManyRequests) {
                    log.warn("쿼터 초과 오류 발생, 재시도 중...");
                    retries--;
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("재시도 대기 중 인터럽트 발생", ie);
                    }
                } else {
                    log.error("OpenAI API 호출에 실패했습니다.", e);
                    break;
                }
            }
        }
        return "OpenAI API 호출 중 에러가 발생했습니다.";
    }

    private String extractQuestionContent(String responseBody) throws IOException {
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode choices = root.path("choices");
        if (choices.isArray() && choices.size() > 0) {
            JsonNode message = choices.get(0).path("message");
            return message.path("content").asText();
        }
        return "질문 내용을 추출할 수 없습니다.";
    }

    public String buildPrompt(Map<String, Object> coupleInfo) {
        // 기존 buildPrompt 메서드 내용
        return "\"당신은 인기 있는 커플 앱을 위한 매력적인 랜덤 질문을 생성하게 됩니다. 주어진 커플의 정보를 바탕으로, " +
                "그들이 서로의 감정과 생각을 깊이 있게 탐구하면서도 재미있게 대화할 수 있는 질문 1개를 만들어주세요. " +
                "이 질문의 요구사항은:\n" +
                "1. 호기심을 자극하고 상대방의 답변이 정말 궁금해지게 만들어야 합니다.\n" +
                "2. 가볍고 재미있으면서도 관계에 대한 통찰을 제공할 수 있어야 합니다.\n" +
                "3. 약간의 도발적인 요소나 유머를 포함할 수 있지만, 결코 불편하거나 부적절하지 않아야 합니다.\n" +
                "4. 랜덤 질문은 데이터 베이스에 저장될 예정이므로 당신의 답변은 81자를 넘지 마세요. \n" +
                "또한, 이 질문은 커플에게 직접 제공될 예정이므로 주어진 개인정보에 대해서는 언급하지 말고 질문외에 쓸데없는 이야기는" +
                "덧붙이지 마세요.\n" +
                "좋은 질문을 만들기 위한 질문의 예시 5가지를 제공하겠습니다." +
                "1. 이젠 말할 수 있다! 난 사실... 너의 관심을 위해 '이런'행동을 했어\n" +
                "2. 당신이 가장 좋아하는 아이스크림은? 서로의 사소한 부분도 궁금해요!\n" +
                "3. 최근, 발견한 상대의 귀여운 모습이 있다면 무엇인가요?\n" +
                "4. 흥!하고 상대방이 토라졌을 때 당신만의 해결법이 있다면 무엇인가요?\n" +
                "5. 오늘 같은 날씨에 함께 요리해서 먹고 싶은 음식은 무엇인가요?\n" +
                "첫 번째 사람의 나이: " + ((List<Integer>) coupleInfo.get("age")).get(0) +
                ", 성별: " + ((List<String>) coupleInfo.get("gender")).get(0) +
                ", MBTI: " + ((List<String>) coupleInfo.get("mbti")).get(0) +
                "; 두 번째 사람의 나이: " + ((List<Integer>) coupleInfo.get("age")).get(1) +
                ", 성별: " + ((List<String>) coupleInfo.get("gender")).get(1) +
                ", MBTI: " + ((List<String>) coupleInfo.get("mbti")).get(1) +
                "; 만나기 시작한 날짜: " + coupleInfo.get("anniversary") +
                "; 자주하는 데이트 카테고리: " + coupleInfo.get("dateCategories");
    }
}

