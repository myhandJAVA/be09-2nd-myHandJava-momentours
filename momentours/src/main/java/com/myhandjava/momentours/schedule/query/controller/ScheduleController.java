package com.myhandjava.momentours.schedule.query.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.service.ScheduleService;
import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduleController {
    ScheduleService scheduleService;
    TodoCourseService todoCourseService;

    @Autowired
    private ScheduleController(ScheduleService scheduleService,
                               TodoCourseService todoCourseService){
        this.scheduleService = scheduleService;
        this.todoCourseService = todoCourseService;
    }

    @GetMapping("/calendar")
    public ResponseEntity<ResponseMessage> findAllSchedule(@RequestAttribute("claims") Claims claims){

        Integer coupleNo = (Integer)claims.get("coupleNo");
        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);
        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(coupleNo);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("ScheduleList",coupleScheduleList);
        responseMap.put("todoCourseList",todoCourseList);

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 조회 성공",responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
