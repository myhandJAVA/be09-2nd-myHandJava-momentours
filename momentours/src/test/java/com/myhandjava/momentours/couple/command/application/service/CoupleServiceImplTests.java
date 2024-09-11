package com.myhandjava.momentours.couple.command.application.service;

import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoupleServiceImplTests {

    @Autowired
    private CoupleService coupleService;

    private static Stream<Arguments> dataSource1() {
        return Stream.of(
                Arguments.of(1,new CoupleDTO(
                        "시바견 커플",
                        "시바.jpg",
                        LocalDateTime.of(2023, 6, 15, 14, 30, 0),
                        1,
                        2,
                        0
                )),
                Arguments.of(2,new CoupleDTO(
                        "고양이 커플",
                        "고양.jpg",
                        LocalDateTime.of(2024, 1, 22, 9, 45, 30),
                        3,
                        4,
                        0
                )),
                Arguments.of(3,new CoupleDTO(
                        "개구리 커플",
                        "개굴.jpg",
                        LocalDateTime.of(2023, 12, 31, 23, 59, 59),
                        5,
                        6,
                        0
                )),
                Arguments.of(4,new CoupleDTO(
                        "오리 커플",
                        "꽥.jpg",
                        LocalDateTime.now(),
                        7,
                        8,
                        0
                ))
        );
    }

    @DisplayName("커플 생성할 때 정보 입력 테스트")
    @ParameterizedTest
    @MethodSource("dataSource1")
    void inputCoupleInformation(int coupleNo, CoupleDTO coupleDTO) {

        assertDoesNotThrow(() -> coupleService.inputCoupleInfo(coupleNo, coupleDTO));
    }

    private static Stream<Arguments> dataSource2() {
        return Stream.of(
                Arguments.of(1,new CoupleDTO(
                        "한국 커플",
                        "서울.jpg",
                        LocalDateTime.of(2022, 8, 15, 14, 30, 0),
                        1,
                        2,
                        0
                )),
                Arguments.of(2,new CoupleDTO(
                        "중국 커플",
                        "상해.jpg",
                        LocalDateTime.of(2020, 11, 2, 9, 45, 30),
                        3,
                        4,
                        0
                )),
                Arguments.of(3,new CoupleDTO(
                        "일본 커플",
                        "동경.jpg",
                        LocalDateTime.of(2021, 3, 31, 23, 59, 59),
                        5,
                        6,
                        0
                )),
                Arguments.of(4,new CoupleDTO(
                        "미국 커플",
                        "워싱턴.jpg",
                        LocalDateTime.now(),
                        7,
                        8,
                        0
                ))
        );
    }

    @DisplayName("커플 정보 수정 테스트")
    @ParameterizedTest
    @MethodSource("dataSource2")
    void updateCoupleInformation(int coupleNO, CoupleDTO coupleDTO) {

        assertDoesNotThrow(() -> coupleService.updateCouple(coupleNO, coupleDTO));
    }

    private static Stream<Arguments> dataSource3() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4)

        );
    }

    @DisplayName("커플 삭제 테스트")
    @ParameterizedTest
    @MethodSource("dataSource3")
    void deleteCoupleInfo(int coupleNo) {

        assertDoesNotThrow(() -> coupleService.deleteCouple(coupleNo));
    }
}