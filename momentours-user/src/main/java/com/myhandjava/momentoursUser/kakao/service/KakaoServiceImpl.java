package com.myhandjava.momentoursUser.kakao.service;

import com.myhandjava.momentoursUser.kakao.client.KakaoApiClient;
import com.myhandjava.momentoursUser.kakao.client.KakaoLoginClient;
import com.myhandjava.momentoursUser.kakao.dto.KakaoTokenResponseDTO;
import com.myhandjava.momentoursUser.kakao.dto.KakaoUserInfoResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class KakaoServiceImpl implements KakaoService {
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoApiClient kakaoApiClient;
    private final Environment env;

    KakaoServiceImpl(KakaoLoginClient kakaoLoginClient,
                     KakaoApiClient kakaoApiClient,
                     Environment env){
        this.kakaoLoginClient = kakaoLoginClient;
        this.kakaoApiClient = kakaoApiClient;
        this.env = env;
    }

    @Override
    public KakaoTokenResponseDTO GetKakaoToken(String code){
        String contentType = "application/x-www-form-urlencoded";
        String grantType = "authorization_code";
        KakaoTokenResponseDTO kakaoTokenResponseDTO =
                kakaoLoginClient.getKakaoToken(contentType,
                        grantType,
                        env.getProperty("kakao.client_id"),
                        env.getProperty("kakao.redirect_uri"),
                        code);
        return kakaoTokenResponseDTO;
    }
    @Override
    public KakaoUserInfoResponseDTO getUserInfo(KakaoTokenResponseDTO token){
        String authorization = "Bearer "+ token.getAccess_token();
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoUserInfoResponseDTO userInfo =
                kakaoApiClient.getUserInfo(contentType,authorization);
        return userInfo;
    }
}
