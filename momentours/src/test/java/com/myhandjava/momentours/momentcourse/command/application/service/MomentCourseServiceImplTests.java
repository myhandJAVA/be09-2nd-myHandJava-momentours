package com.myhandjava.momentours.momentcourse.command.application.service;

import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourse;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomentCourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MomentCourseServiceImplTests {

    @Autowired
    private MomentCourseService momentCourseService;

    @Autowired
    private MomentCourseRepository momentCourseRepository;

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
        momentCourseDTO.setMomCoursePublic(true);
        momentCourseDTO.setMomCourseMemo("여기에서는 휘낭시에를 먹어야해!");
        momentCourseDTO.setMomCourseCoupleNo(2);
        momentCourseDTO.setMomentNos(momentNos);

        Assertions.assertDoesNotThrow(
                () -> momentCourseService.registMomentCourse(momentCourseDTO)
        );
    }

    @DisplayName("추억 코스 삭제 테스트")
    @Test
    @Transactional
    void removeMomentCourse() {
        Assertions.assertDoesNotThrow(() -> momentCourseService.removeMomentCourse(1, 1));

        Optional<MomentCourse> momentCourseOpt = momentCourseRepository.findByMomCourseNoAndMomCourseCoupleNo(1, 1);
        MomentCourse momentCourse = momentCourseOpt.orElseThrow(() -> new RuntimeException("추억코스가 없습니다."));
        if(momentCourse.isMomCourseIsDeleted()) {
        System.out.println("isMomCourseIsDeleted 속성이 true로 삭제 완료!");
        }
    }

    @DisplayName("추억 코스 수정 테스트")
    @Test
    @Transactional
    void modifyMomentCourse() {
        List<Integer> momentNos = new ArrayList<>();
        momentNos.add(3);
        momentNos.add(4);

        MomentCourseDTO momentCourseDTO = new MomentCourseDTO();

        momentCourseDTO.setMomCourseTitle("아아 수정수정");
        momentCourseDTO.setMomCourseSort(MomentCourseSort.oneDay);
        momentCourseDTO.setMomCoursePublic(false);
        momentCourseDTO.setMomCourseMemo("여기에서는 휘낭시에를 먹어야해!");
        momentCourseDTO.setMomCourseCoupleNo(2);
        momentCourseDTO.setMomentNos(momentNos);

        Assertions.assertDoesNotThrow(
                () -> momentCourseService.modifyMomentCourse(14, momentCourseDTO)
        );

        System.out.println("momentCourseDTO: " + momentCourseDTO);

        MomentCourse momentCourse = momentCourseRepository.findById(14).orElseThrow();
        Assertions.assertEquals("아아 수정수정", momentCourse.getMomCourseTitle());
    }

    @DisplayName("추억 코스 좋아요 기능 테스트")
    @Test
    @Transactional
    void incrementLike() {
        Assertions.assertDoesNotThrow(() -> momentCourseService.incrementLike(1));
    }
}