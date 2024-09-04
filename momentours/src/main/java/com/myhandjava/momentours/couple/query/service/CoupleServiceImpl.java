package com.myhandjava.momentours.couple.query.service;

import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import com.myhandjava.momentours.couple.query.repository.CoupleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
