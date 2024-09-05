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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @DisplayName("예정 코스 수정 확인 테스트")
    @Test
    @Transactional
    void modifyTodoCourse() {
        List<Integer> todoNos = new ArrayList<>();
        todoNos.add(1);
        todoNos.add(2);

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseTitle("수정수정수정!!!");
        todoCourseDTO.setToDoCourseSort(MomentCourseSort.oneDay);
        todoCourseDTO.setToDoCourseStartDate(null);
        todoCourseDTO.setToDoCourseEndDate(null);
        todoCourseDTO.setToDoCourseMemo("이렇게 수정하는거 맞나?");
        todoCourseDTO.setToDoCourseCoupleNo(1);
        todoCourseDTO.setTodoNos(todoNos);

        Assertions.assertDoesNotThrow(
                () -> todoCourseService.modifyTodoCourse(1, todoCourseDTO)
        );

        System.out.println("todoCourseDTO = " + todoCourseDTO);

        TodoCourse todoCourse = todoCourseRepository.findById(1).orElseThrow();
        Assertions.assertEquals("수정수정수정!!!", todoCourse.getToDoCourseTitle());
    }

    @DisplayName("예정 코스 삭제 테스트")
    @Test
    @Transactional
    void removeTodoCourse() {
        Assertions.assertDoesNotThrow(() -> todoCourseService.removeTodoCourse(4, 1));

        Optional<TodoCourse> todoCourseOpt = todoCourseRepository.findByToDoCourseNoAndToDoCourseCoupleNo(
                4, 1);
        TodoCourse todoCourse = todoCourseOpt.orElseThrow(() -> new RuntimeException("예정코스가 없습니다."));
        if (todoCourse.isToDoCourseIsDeleted()) {
            System.out.println("isToDoCourseIsDeleted 속성이 true로 삭제 완료!");
        }
    }

    @DisplayName("예정 코스 좋아요 기능 테스트")
    @Test
    @Transactional
    void incrementLike() {
        Assertions.assertDoesNotThrow(() -> todoCourseService.incrementLike(1));
    }

}