package com.myhandjava.momentours.couple.query.service;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;

import java.util.Map;

public interface CoupleService {
    CoupleDTO findCoupleByCoupleNo(int coupleNo);
    // 랜덤질문 생성을 위한 커플 정보 가공 메서드
    Map<String, Object> getCoupleInfo(int coupleNo);
}
