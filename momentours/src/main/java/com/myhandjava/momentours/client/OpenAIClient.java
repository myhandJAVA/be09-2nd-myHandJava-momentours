package com.myhandjava.momentours.client;

import com.myhandjava.momentours.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "chat-gpt", url = "${openai.api.url}", configuration = FeignClientConfig.class)
public interface OpenAIClient {
    @PostMapping(produces = "application/json")
    ResponseEntity<String> generateQuestion(
            @RequestBody Map<String, Object> requestBody
    );
}

