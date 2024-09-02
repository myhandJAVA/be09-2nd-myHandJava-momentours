package com.myhandjava.momentours.todocourse.query.service;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoCourseServiceImplTests {

    @Autowired
    private TodoCourseService todoCourseService;

    @DisplayName("예정 코스 전체 조회 확인 테스트")
    @Test
    public void testFindAllTodoCourse() {
        int TodoCourseCoupleNo = 1;
        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(TodoCourseCoupleNo);
        assertNotNull(todoCourseList);

        todoCourseList.forEach(System.out::println);
    }

    @DisplayName("예정 코스 상세 조회 확인 테스트")
    @Test
    public void testFindTodoCourseByTodoCourseNo() {
        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseCoupleNo(1);
        todoCourseDTO.setToDoCourseNo(1);

        List<TodoCourseDTO> todoCourseList = todoCourseService.findTodoCourseByTodoCourseNo(todoCourseDTO);
        assertNotNull(todoCourseList);

        todoCourseList.forEach(System.out::println);
    }

}