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
    public ResponseEntity<ResponseMessage> registTodoCourse(@ModelAttribute RequestRegistTodoCourseVO newTodoCourse,
                                                            @RequestAttribute("claims") Claims claims) {

        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));

        TodoCourseDTO todoCourseDTO = modelMapper.map(newTodoCourse, TodoCourseDTO.class);
        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
        todoCourseService.registTodoCourse(todoCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newTodoCourse", newTodoCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "등록 성공!", responseMap);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    // 예정 코스 수정
    @PutMapping("/{todoCourseNo}")
    public ResponseEntity<ResponseMessage> modifyTodoCourse(@PathVariable int todoCourseNo,
                                                            @RequestBody RequestModifyTodoCourseVO modifyTodoCourse,
                                                            @RequestAttribute("claims") Claims claims) {

        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        TodoCourseDTO todoCourseDTO = modelMapper.map(modifyTodoCourse, TodoCourseDTO.class);
        todoCourseDTO.setToDoCourseCoupleNo(coupleNo);
        todoCourseService.modifyTodoCourse(todoCourseNo, todoCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyTodoCourse", modifyTodoCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "예정 코스 수정 성공!", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    // 예정 코스 삭제
    @DeleteMapping("/{todoCourseNo}")
    public ResponseEntity<?> removeTodoCourse(@PathVariable int todoCourseNo,
                                              @RequestAttribute("claims") Claims claims) {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        todoCourseService.removeTodoCourse(todoCourseNo, coupleNo);

        return ResponseEntity.noContent().build();
    }

    // 예정 코스 좋아요
    @PostMapping("/like/{todoCourseNo}")
    public ResponseEntity<ResponseMessage> likeTodoCourse(@PathVariable int todoCourseNo,
                                                          @CookieValue(value = "todoCourseLike", defaultValue = "") String toDoCourseLike,
                                                          HttpServletResponse response) {

        Set<Integer> todoCourseLikeIds = Arrays.stream(toDoCourseLike.split("-"))
                .filter(id -> !id.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        if (!todoCourseLikeIds.contains(todoCourseNo)) {
            // 좋아요 추가
            todoCourseService.incrementLike(todoCourseNo);
            todoCourseLikeIds.add(todoCourseNo);
        } else {
            // 좋아요 취소
            todoCourseService.decrementLike(todoCourseNo);
            todoCourseLikeIds.remove(todoCourseNo);
        }

        String modifyTodoCourseLike = todoCourseLikeIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));

        Cookie cookie = new Cookie("todoCourseLike", modifyTodoCourseLike);
        cookie.setPath("/");

        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
