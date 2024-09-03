package com.myhandjava.momentours.momentcourse.command.application.service;

import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MomentCourseServiceImplTests {

    @Autowired
    private MomentCourseService momentCourseService;

    @Autowired
    private ModelMapper modelMapper;

    @DisplayName("추억 코스 등록 테스트")
    @Test
    @Transactional
    void registMomentCourse() {
        List<Integer> momentNos = new ArrayList<>();
        momentNos.add(1);
        momentNos.add(2);
        momentNos.add(3);

        MomentCourseDTO momentCourseDTO = new MomentCourseDTO();

        momentCourseDTO.setMomCourseTitle("룰루랄라 추억 데이트 코스~");
        momentCourseDTO.setMomCourseSort(MomentCourseSort.oneDay);
        momentCourseDTO.setMomCourseLike(12);
        momentCourseDTO.setMomCoursePublic(true);
        momentCourseDTO.setMomCourseMemo("여기에서는 휘낭시에를 먹어야해!");
        momentCourseDTO.setMomCourseCoupleNo(2);
        momentCourseDTO.setMomentNos(momentNos);

        Assertions.assertDoesNotThrow(
                () -> momentCourseService.registMomentCourse(momentCourseDTO)
        );
    }

}