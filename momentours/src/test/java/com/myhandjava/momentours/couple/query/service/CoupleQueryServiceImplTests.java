package com.myhandjava.momentours.couple.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleQueryServiceImplTests {

    @Autowired
    private CoupleServiceImpl coupleService;

    private static Stream<Arguments> dataSource() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4)
        );
    }

    @DisplayName("커플 번호로 커플 정보 조회")
    @ParameterizedTest
    @MethodSource("dataSource")
    void GetCoupleByCoupleNo(int coupleNo) {
        assertDoesNotThrow(() -> coupleService.findCoupleByCoupleNo(coupleNo));
    }

    @DisplayName("랜덤 질문을 위한 커플 번호로 회원 정보 추출")
    @ParameterizedTest
    @MethodSource("dataSource")
    void getUserInfoByCoupleNo(int coupleNo) {
        assertDoesNotThrow(() -> coupleService.getCoupleInfo(coupleNo));
    }
}