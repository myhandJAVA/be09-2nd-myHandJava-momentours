package com.myhandjava.momentours.moment.query.service;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MomentServiceImplTests {

    @Autowired
    private MomentService momentService;

    @DisplayName("추억 조회 테스트")
    @Test
    void 커플_번호로_추억_조회() {
        MomentDTO momentDTO = new MomentDTO();

        momentDTO.setMomentCoupleNo(1);
        List<MomentDTO> result = momentService.findAllMomentByCoupleNo(momentDTO);

        Assertions.assertNotNull(result);
    }

}