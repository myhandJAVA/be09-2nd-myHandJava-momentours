package com.myhandjava.momentours.todocourse.query.controller;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todocourse")
public class TodoCourseController {

    private final TodoCourseService todoCourseService;

    @Autowired
    public TodoCourseController(TodoCourseService todoCourseService) {
        this.todoCourseService = todoCourseService;
    }

    // 해당 커플의 예정 코스 전체 조회
    @GetMapping("/todoCourse")
    public List<TodoCourseDTO> findAllTodoCourse(@RequestAttribute("claims") Claims userNo) {

        int todoCourseNo = Integer.parseInt(userNo.getAudience());

        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(todoCourseNo);

        return todoCourseList;
    }

    // 해당 커플의 예정 코스 상세 조회
    @GetMapping("/todoCourse/{TodoCourseNo}")
    public List<TodoCourseDTO> findTodoCourseByTodoCourseNo(@RequestAttribute("claims") Claims userNo,
                                                            @PathVariable int TodoCourseNo) {

        int todoCourseNo = Integer.parseInt(userNo.getAudience());

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseCoupleNo(todoCourseNo);
        todoCourseDTO.setToDoCourseNo(TodoCourseNo);

        List<TodoCourseDTO> todoCourseList = todoCourseService.findTodoCourseByTodoCourseNo(todoCourseDTO);

        return todoCourseList;
    }

    @GetMapping("/search")
    public List<TodoCourseDTO> searchTodoCourse(@RequestParam(required = false) String searchCondition,
                                                @RequestParam(required = false) String keyword) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("keyword", keyword);

        List<TodoCourseDTO> searchList = todoCourseService.searchToDoCourse(searchMap);

        return searchList;
    }
}
