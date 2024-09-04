package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.domain.repository.CoupleRepository;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleCommandServiceImplTests {

    @Autowired
    private  CoupleServiceImpl coupleService;
    @Autowired
    private  CoupleRepository coupleRepository;
    @Autowired
    private  ModelMapper modelMapper;



    @DisplayName("회원 번호2개를 받고 커플 정보 입력 테스트")
    @Test
    void inputCoupleInfo() {
        int userNo1 = 9;
        int userNo2 = 10;
        CoupleDTO coupleDTO = new CoupleDTO();

        coupleDTO.setCouplePhoto("마루.jpg");
        coupleDTO.setCoupleName("무슨 이름으로 해야 소문이 나지?");
        coupleDTO.setCoupleStartDate(LocalDateTime.now());

        coupleService.inputCoupleInfo(userNo1, userNo2, coupleDTO);
    }

    @DisplayName("커플 정보 수정 테스트")
    @Test
    void updateCoupleInfo() {
        int coupleNo = 1;
        CoupleDTO coupleDTO = new CoupleDTO();
        coupleDTO.setCoupleName("동혁민근커플");
        coupleDTO.setCouplePhoto("완전소중jpg");
        coupleDTO.setCoupleStartDate(LocalDateTime.now());

        coupleService.updateCouple(coupleNo, coupleDTO);
    }
}