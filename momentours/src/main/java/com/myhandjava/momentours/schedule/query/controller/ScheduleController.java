package com.myhandjava.momentours.schedule.query.controller;

import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.query.service.CoupleService;
import com.myhandjava.momentours.schedule.command.domain.service.ScheduleValidService;
import com.myhandjava.momentours.schedule.command.domain.vo.UserCoupleNoVO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.service.ScheduleService;
import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final TodoCourseService todoCourseService;
    private final CoupleService coupleService;
    private final ScheduleValidService scheduleValidService;

    @Autowired
    private ScheduleController(ScheduleService scheduleService,
                               TodoCourseService todoCourseService,
                               CoupleService coupleService,
                               ScheduleValidService scheduleValidService){
        this.scheduleService = scheduleService;
        this.todoCourseService = todoCourseService;
        this.coupleService = coupleService;
        this.scheduleValidService = scheduleValidService;
    }

    @GetMapping("/calendar")
    public ResponseEntity<ResponseMessage> findAllSchedule(@RequestBody UserCoupleNoVO userCoupleNoVO,
                                                           @RequestHeader("Authorization")String token){
        boolean isValid = scheduleValidService.isValidUserNoAndCoupleNo(userCoupleNoVO.getUserNo(), userCoupleNoVO.getCoupleNo(), token);
        if(!isValid) {
            ResponseMessage unValidaMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 조회할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(unValidaMessage);
        }
        int coupleNo = userCoupleNoVO.getCoupleNo();
        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);
        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(coupleNo);
        LocalDateTime metDay = coupleService.findCoupleByCoupleNo(coupleNo).getCoupleStartDate();

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("ScheduleList",coupleScheduleList);
        responseMap.put("todoCourseList",todoCourseList);
        responseMap.put("metDay",metDay);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 조회 성공",responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
