package com.myhandjava.momentoursUser.kakao.client;

import com.myhandjava.momentoursUser.kakao.dto.KakaoUserInfoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "kakaoApi",url="https://kapi.kakao.com")
public interface KakaoApiClient {
    @GetMapping("v2/user/me")
    KakaoUserInfoResponseDTO getUserInfo(@RequestHeader("Content-Type") String contentType,
                                         @RequestHeader("Authorization") String authorization);

}
