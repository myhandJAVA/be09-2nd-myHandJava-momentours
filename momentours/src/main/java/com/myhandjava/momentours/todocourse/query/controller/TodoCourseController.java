package com.myhandjava.momentours.todocourse.query.controller;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todocourse")
public class TodoCourseController {

    private final TodoCourseService todoCourseService;

    @Autowired
    public TodoCourseController(TodoCourseService todoCourseService) {
        this.todoCourseService = todoCourseService;
    }

    // 해당 커플의 예정 코스 전체 조회
    @GetMapping("/todoCourse/{TodoCourseCoupleNo}")
    public List<TodoCourseDTO> findAllTodoCourse(@PathVariable int TodoCourseCoupleNo) {
        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(TodoCourseCoupleNo);

        return todoCourseList;
    }

    // 해당 커플의 예정 코스 상세 조회
    @GetMapping("/todoCourse/{TodoCourseCoupleNo}/{TodoCourseNo}")
    public List<TodoCourseDTO> findTodoCourseByTodoCourseNo(@PathVariable int TodoCourseCoupleNo,
                                                            @PathVariable int TodoCourseNo) {

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseCoupleNo(TodoCourseCoupleNo);
        todoCourseDTO.setToDoCourseNo(TodoCourseNo);

        List<TodoCourseDTO> todoCourseList = todoCourseService.findTodoCourseByTodoCourseNo(todoCourseDTO);

        return todoCourseList;
    }
}
