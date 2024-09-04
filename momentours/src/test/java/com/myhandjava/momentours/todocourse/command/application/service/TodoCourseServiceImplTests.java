package com.myhandjava.momentours.todocourse.command.application.service;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.todocourse.command.application.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.command.domain.aggregate.TodoCourse;
import com.myhandjava.momentours.todocourse.command.domain.repository.TodoCourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoCourseServiceImplTests {

    @Autowired
    private TodoCourseService todoCourseService;

    @Autowired
    private TodoCourseRepository todoCourseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @DisplayName("예정 코스 등록 확인 테스트")
    @Test
    @Transactional
    void registTodoCourse() {

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();
        todoCourseDTO.setToDoCourseTitle("속초 당일여행 가보즈아!");
        todoCourseDTO.setToDoCourseSort(MomentCourseSort.oneDay);
        todoCourseDTO.setToDoCourseStartDate(null);
        todoCourseDTO.setToDoCourseEndDate(null);
        todoCourseDTO.setToDoCourseMemo("오랜만에 놀러간다!");
        todoCourseDTO.setToDoCourseCoupleNo(1);

        TodoCourse todoCourse = modelMapper.map(todoCourseDTO, TodoCourse.class);

        Assertions.assertDoesNotThrow(
                () -> todoCourseService.registTodoCourse(todoCourseDTO)
        );

        TodoCourse registedTodoCourse = todoCourseRepository.findById(todoCourse.getToDoCourseNo()).orElseThrow();
        Assertions.assertEquals("속초 당일여행 가보즈아!", registedTodoCourse.getToDoCourseTitle());
    }

}