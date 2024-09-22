package com.myhandjava.momentoursUser.kakao.controller;

import com.myhandjava.momentoursUser.kakao.client.KakaoLoginClient;
import com.myhandjava.momentoursUser.kakao.dto.KakaoTokenResponseDTO;
import com.myhandjava.momentoursUser.kakao.dto.KakaoUserInfoResponseDTO;
import com.myhandjava.momentoursUser.kakao.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;
    private final Environment env;
    @Autowired
    KakaoController(KakaoLoginClient kakaoLoginClient,
                    Environment env,
                    KakaoService kakaoService){
        this.env = env;
        this.kakaoService = kakaoService;
    }

    @GetMapping("kakao/login")
    public String loginKakao(){
        String loginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                +env.getProperty("kakao.client_id")
                +"&redirect_uri="
                +env.getProperty("kakao.redirect_uri");

        return "redirect:" + loginUrl;
    }

    @GetMapping("/kakao/oauth2")
    public void GetKakaoAuth(@RequestParam String code){
        KakaoTokenResponseDTO kakaoTokenResponseDTO = kakaoService.GetKakaoToken(code);
        KakaoUserInfoResponseDTO userInfo = kakaoService.getUserInfo(kakaoTokenResponseDTO);

        
    }
}
