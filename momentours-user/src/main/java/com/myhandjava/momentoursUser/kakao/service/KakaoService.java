package com.myhandjava.momentoursUser.kakao.service;

import com.myhandjava.momentoursUser.kakao.dto.KakaoTokenResponseDTO;
import com.myhandjava.momentoursUser.kakao.dto.KakaoUserInfoResponseDTO;

public interface KakaoService {
    KakaoTokenResponseDTO GetKakaoToken(String code);

    KakaoUserInfoResponseDTO getUserInfo(KakaoTokenResponseDTO token);
}
