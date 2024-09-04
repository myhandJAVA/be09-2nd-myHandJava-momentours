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
    public List<TodoCourseDTO> findAllTodoCourse(@RequestAttribute("claims") Claims claims) {

        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));

        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(coupleNo);

        return todoCourseList;
    }

    // 해당 커플의 예정 코스 상세 조회
    @GetMapping("/todoCourse/{TodoCourseNo}")
    public List<TodoCourseDTO> findTodoCourseByTodoCourseNo(@RequestAttribute("claims") Claims claims,
                                                            @PathVariable int TodoCourseNo) {

        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
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
