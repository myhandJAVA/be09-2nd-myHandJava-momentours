package com.myhandjava.momentours.todocourse.query.controller;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.Path;
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
    @GetMapping("")
    public List<TodoCourseDTO> findAllTodoCourse(@RequestParam int coupleNo) {

        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(coupleNo);

        return todoCourseList;
    }

    // 해당 커플의 예정 코스 상세 조회
    @GetMapping("/{coupleNo}/{TodoCourseNo}")
    public List<TodoCourseDTO> findTodoCourseByTodoCourseNo(@PathVariable int coupleNo,
                                                            @PathVariable int TodoCourseNo) {

        TodoCourseDTO todoCourseDTO = new TodoCourseDTO();

        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
        todoCourseDTO.setToDoCourseNo(TodoCourseNo);

        List<TodoCourseDTO> todoCourseList = todoCourseService.findTodoCourseByTodoCourseNo(todoCourseDTO);

        return todoCourseList;
    }

    // 예정 코스 검색
    @GetMapping("/search/{coupleNo}")
    public List<TodoCourseDTO> searchTodoCourse(@RequestParam(required = false) String searchCondition,
                                                @RequestParam(required = false) String keyword,
                                                @PathVariable int coupleNo) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("keyword", keyword);
        searchMap.put("coupleNo", coupleNo);

        List<TodoCourseDTO> searchList = todoCourseService.searchToDoCourse(searchMap);

        return searchList;
    }
}
