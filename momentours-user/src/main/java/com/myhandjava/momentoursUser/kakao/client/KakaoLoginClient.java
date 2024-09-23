package com.myhandjava.momentoursUser.kakao.client;

import com.myhandjava.momentoursUser.kakao.dto.KakaoTokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="kakaoLogin",url="https://kauth.kakao.com")
public interface KakaoLoginClient {

    @PostMapping("/oauth/token")
    KakaoTokenResponseDTO getKakaoToken(@RequestHeader("Content-Type") String contentType,
                                        @RequestParam("grant_type") String grantType,
                                        @RequestParam("client_id") String clientId,
                                        @RequestParam("redirect_uri") String redirectUri,
                                        @RequestParam("code") String code);
}
