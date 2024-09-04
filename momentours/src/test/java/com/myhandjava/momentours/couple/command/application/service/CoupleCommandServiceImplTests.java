package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.domain.repository.CoupleRepository;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleCommandServiceImplTests {

    private final CoupleServiceImpl coupleService;

    private final CoupleRepository coupleRepository;

    private final ModelMapper modelMapper;

    CoupleCommandServiceImplTests(CoupleServiceImpl coupleService, CoupleRepository coupleRepository, ModelMapper modelMapper) {
        this.coupleService = coupleService;
        this.coupleRepository = coupleRepository;
        this.modelMapper = modelMapper;
    }

    @DisplayName("회원 번호2개로 커플 정보 입력창으로 안내")
    @Test
    void inputCoupleInfo() {
        int userNo1 = 9;
        int userNo2 = 10;
        CoupleRegistVO coupleRegistVO = new CoupleRegistVO();

    }
}