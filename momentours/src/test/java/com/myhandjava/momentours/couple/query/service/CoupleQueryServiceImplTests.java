package com.myhandjava.momentours.couple.query.service;

import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleQueryServiceImplTests {

    @Autowired
    private CoupleServiceImpl coupleService;

    @DisplayName("커플 번호로 커플 정보 조회")
    @Test
    void GetCoupleByCoupleNo() {
        int coupleNo = 1;
        CoupleDTO coupleDTO = coupleService.findCoupleByCoupleNo(coupleNo);

        assertNotNull(coupleDTO);
        assertEquals(coupleNo, coupleDTO.getCoupleNo());
    }

}