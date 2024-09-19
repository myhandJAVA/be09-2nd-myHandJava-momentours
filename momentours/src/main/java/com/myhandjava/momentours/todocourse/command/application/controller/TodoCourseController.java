package com.myhandjava.momentours.todocourse.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.todocourse.command.application.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.command.application.service.TodoCourseService;
import com.myhandjava.momentours.todocourse.command.domain.vo.RequestModifyTodoCourseVO;
import com.myhandjava.momentours.todocourse.command.domain.vo.RequestRegistTodoCourseVO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController("todoCourseCommandController")
@RequestMapping("/todocourse")
@Slf4j
public class TodoCourseController {

    private final TodoCourseService todoCourseService;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoCourseController(TodoCourseService todoCourseService, ModelMapper modelMapper) {
        this.todoCourseService = todoCourseService;
        this.modelMapper = modelMapper;
    }

    // 예정 코스 등록
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registTodoCourse(@RequestBody RequestRegistTodoCourseVO newTodoCourse) {

        TodoCourseDTO todoCourseDTO = modelMapper.map(newTodoCourse, TodoCourseDTO.class);
//        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
        todoCourseService.registTodoCourse(todoCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newTodoCourse", newTodoCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "등록 성공!", responseMap);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // 예정 코스 수정
    @PutMapping("/{todoCourseNo}")
    public ResponseEntity<ResponseMessage> modifyTodoCourse(@PathVariable int todoCourseNo,
                                                            @RequestBody RequestModifyTodoCourseVO modifyTodoCourse) {

        TodoCourseDTO todoCourseDTO = modelMapper.map(modifyTodoCourse, TodoCourseDTO.class);
//        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
        todoCourseService.modifyTodoCourse(todoCourseNo, todoCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyTodoCourse", modifyTodoCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "예정 코스 수정 성공!", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 예정 코스 삭제
    @DeleteMapping("/{todoCourseNo}")
    public ResponseEntity<?> removeTodoCourse(@PathVariable int todoCourseNo,
                                              @RequestBody int coupleNo) {
        todoCourseService.removeTodoCourse(todoCourseNo, coupleNo);

        return ResponseEntity.noContent().build();
    }
}
