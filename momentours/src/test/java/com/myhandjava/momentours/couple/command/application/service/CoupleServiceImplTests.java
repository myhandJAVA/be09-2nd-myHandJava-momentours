package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleServiceImplTests {

    @Autowired
    private CoupleService coupleService;

    @DisplayName("커플 생성할 때 정보 입력 테스트")
    @Test
    void inputCoupleInformation() {
        int userNo1 = 9;
        int userNo2 = 10;
        CoupleDTO coupleDTO = new CoupleDTO();
        coupleDTO.setCouplePhoto("강아지.jpg");
        coupleDTO.setCoupleName("강아지 커플");
        coupleDTO.setCoupleStartDate(LocalDateTime.now());

        assertDoesNotThrow(() -> coupleService.inputCoupleInfo(userNo1, userNo2, coupleDTO));
    }

    @DisplayName("커플 정보 수정 테스트")
    @Test
    void updateCoupleInformation() {
        int coupleNO = 1;
        CoupleDTO coupleDTO = new CoupleDTO();
        coupleDTO.setCoupleUserNo2(1);
        coupleDTO.setCoupleUserNo1(2);
        coupleDTO.setCoupleIsDeleted(0);
        coupleDTO.setCoupleName("고양이 커플");
        coupleDTO.setCoupleStartDate(LocalDateTime.now());
        coupleDTO.setCouplePhoto("고양이.jpg");

        assertDoesNotThrow(() -> coupleService.updateCouple(coupleNO, coupleDTO));
    }
}