package com.myhandjava.momentours.couple.query.service;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import com.myhandjava.momentours.couple.query.repository.CoupleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service(value = "coupleQueryService")
@Slf4j
public class CoupleServiceImpl implements CoupleService {

    private final CoupleMapper coupleMapper;

    @Autowired
    public CoupleServiceImpl(CoupleMapper coupleMapper) {
        this.coupleMapper = coupleMapper;
    }

    // 커플 마이페이지 느낌으로 조회하기

    @Override
    public CoupleDTO findCoupleByCoupleNo(int coupleNo) {
        CoupleDTO coupleDTO = coupleMapper.findCoupleByCoupleNo(coupleNo);
        log.info("조회된 커플 정보: {}" , coupleDTO);
        return coupleDTO;
    }

    @Override
    public Map<String, Object> getCoupleInfo(int coupleNo) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> coupleInfo = coupleMapper.getCoupleInfo(coupleNo);
        List<String> topCategories = coupleMapper.getTopDateCategories(coupleNo);

        result.put("age", Arrays.asList(
                calculateAge((Date)coupleInfo.get("user1_birth")),
                calculateAge((Date)coupleInfo.get("user2_birth"))
        ));
        result.put("gender", Arrays.asList(coupleInfo.get("user1_gender"), coupleInfo.get("user2_gender")));
        result.put("mbti", Arrays.asList(coupleInfo.get("user1_mbti"), coupleInfo.get("user2_mbti")));
        result.put("anniversary", coupleInfo.get("anniversary"));
        result.put("dateCategories", topCategories);

        log.info("랜덤질문을 위한 회원 정보: {}", result);
        return result;
    }

    private Integer calculateAge(Date birthDate) {
        return Period.between(birthDate.toLocalDate(), LocalDate.now()).getYears();
    }
}
