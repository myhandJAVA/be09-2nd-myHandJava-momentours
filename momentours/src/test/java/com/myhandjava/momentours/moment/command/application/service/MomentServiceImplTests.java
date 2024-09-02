package com.myhandjava.momentours.moment.command.application.service;

import com.myhandjava.momentours.moment.command.domain.repository.MomentRepository;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MomentServiceImplTests {

    @Autowired
    private MomentService momentService;
    @Autowired
    private MomentRepository momentRepository;

    @DisplayName("추억 조회 테스트")
    @Test
    void findMomentByCoupleNo() {
        // given
        int momentCoupleNo = 1;

        // when
        List<ResponseFindMomentByCoupleNoVO> result = momentService.findMomentByMomentCoupleNo(momentCoupleNo);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

//        System.out.println("moment_category: " + result.get(0).getMomentCategory());
        result.forEach(vo -> assertEquals(momentCoupleNo, vo.getMomentCoupleNo()));
    }

    @DisplayName("공개 여부로 추억 조회 테스트")
    @Test
    void findMomentByMomentCoupleNo() {
        // given
        int momentPublic = 1;

        // when
        List<ResponseFindMomentByMomentPublicVO> result = momentService.findMomentByMomentPublic(momentPublic);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());

        System.out.println("result 공개 여부: " + result.get(0).getMomentPublic());
        result.forEach(vo -> assertTrue(vo.getMomentPublic() == 1));
    }

}