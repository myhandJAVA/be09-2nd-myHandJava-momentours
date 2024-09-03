package com.myhandjava.momentours.randomquestion.command.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;

    public OpenAIService(RestTemplate restTemplate,
                         @Value("${openai.api.key}") String apiKey,
                         @Value("${openai.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public String generateQuestionForCouple(Map<String, Object> coupleInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // OpenAI API에 맞는 프롬프트 작성
        String prompt = buildPrompt(coupleInfo);

        // Chat API에 맞는 메시지 형식으로 변경
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
        requestBody.put("max_tokens", 500); // max_tokens는 사용할 토큰의 최대 수를 의미합니다.

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        int retries = 3;
        while (retries > 0) {
            try {
                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
                return response.getBody();
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException.TooManyRequests) {
                    logger.warn("쿼터 초과 오류 발생, 재시도 중...");
                    retries--;
                    try {
                        TimeUnit.SECONDS.sleep(30); // 30초 대기 후 재시도
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("재시도 대기 중 인터럽트 발생", ie);
                    }
                } else {
                    logger.error("OpenAI API 호출에 실패했습니다.", e);
                    break;
                }
            }
        }

        return "OpenAI API 호출 중 에러가 발생했습니다.";
    }

    private String buildPrompt(Map<String, Object> coupleInfo) {
        // 커플 정보를 기반으로 질문을 생성할 프롬프트를 작성합니다.
        return "\"당신은 인기 있는 커플 앱을 위한 매력적인 랜덤 질문을 생성하게 됩니다. 주어진 커플의 정보를 바탕으로, " +
                "그들이 서로의 감정과 생각을 깊이 있게 탐구하면서도 재미있게 대화할 수 있는 질문을 만들어주세요. " +
                "이 질문은:\n" +
                "1. 호기심을 자극하고 상대방의 답변이 정말 궁금해지게 만들어야 합니다.\n" +
                "2. 가볍고 재미있으면서도 관계에 대한 통찰을 제공할 수 있어야 합니다.\n" +
                "3. 약간의 도발적인 요소나 유머를 포함할 수 있지만, 결코 불편하거나 부적절하지 않아야 합니다.\n" +
                "4. 커플이 서로에 대해 새로운 면을 발견하거나 잊고 있던 추억을 떠올리게 할 수 있어야 합니다.\n" +
                "5. 대화를 시작하기 쉽고, 자연스럽게 더 깊은 대화로 이어질 수 있는 질문이어야 합니다.\n" +
                "또한, 이 질문은 커플에게 직접 제공될 예정이므로 주어진 개인정보에 대해서는 언급하지 마세요.\n" +
                "질문의 예시 5가지를 제공하겠습니다." +
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
