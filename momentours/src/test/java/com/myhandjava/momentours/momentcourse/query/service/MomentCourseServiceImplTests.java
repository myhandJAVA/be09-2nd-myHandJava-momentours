package com.myhandjava.momentours.momentcourse.query.service;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MomentCourseServiceImplTests {

    @Autowired
    private MomentCourseService momentCourseService;

    @DisplayName("추억 코스 전체 코스 확인 테스트")
    @Test
    public void testFindAllMomentCourse() {
        int momCourseCoupleNo = 1;
        List<MomentCourseDTO> momentCourseList = momentCourseService.findAllMomentCourse(momCourseCoupleNo);
        assertNotNull(momentCourseList);

        momentCourseList.forEach(System.out::println);
    }

    @DisplayName("추억 코스 상세 코스 확인 테스트")
    @Test
    public void testFindMomentCourseByMomCourseNo() {
        MomentCourseDTO momentCourseDTO = new MomentCourseDTO();

        momentCourseDTO.setMomCourseCoupleNo(3);
        momentCourseDTO.setMomCourseNo(3);

        List<MomentCourseDTO> momentCourseList = momentCourseService.findMomentCourseByMomCourseNo(momentCourseDTO);
        assertNotNull(momentCourseList);

        momentCourseList.forEach(System.out::println);
    }

}