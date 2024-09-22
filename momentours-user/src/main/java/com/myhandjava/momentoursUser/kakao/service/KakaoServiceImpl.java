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
    // 비밀번호를 현재 토큰으로 바꾸고 로그인 시도!! / 닉네임도 바꾸기
    // 회원이 아니라면 현재토큰을 비밀번호로 넣고 나머지는 입력받아서 로그인하게 하자!
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
        log.info("accessToken:{}",token.getAccess_token());
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoUserInfoResponseDTO userInfo =
                kakaoApiClient.getUserInfo(contentType,authorization);
        return userInfo;
    }
}
