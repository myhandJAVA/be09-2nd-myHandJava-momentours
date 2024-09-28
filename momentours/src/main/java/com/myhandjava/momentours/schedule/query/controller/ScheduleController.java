package com.myhandjava.momentours.schedule.query.controller;

import com.myhandjava.momentours.client.UserClient;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.query.service.CoupleService;
import com.myhandjava.momentours.schedule.command.domain.service.ScheduleValidService;
import com.myhandjava.momentours.schedule.command.domain.vo.UserCoupleNoVO;
import com.myhandjava.momentours.schedule.query.dto.CalendarDTO;
import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.service.ScheduleService;
import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.service.TodoCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final TodoCourseService todoCourseService;
    private final CoupleService coupleService;
    private final ScheduleValidService scheduleValidService;
    private final UserClient userClient;

    @Autowired
    private ScheduleController(ScheduleService scheduleService,
                               TodoCourseService todoCourseService,
                               CoupleService coupleService,
                               ScheduleValidService scheduleValidService, @Qualifier("com.myhandjava.momentours.client.UserClient") UserClient userClient){
        this.scheduleService = scheduleService;
        this.todoCourseService = todoCourseService;
        this.coupleService = coupleService;
        this.scheduleValidService = scheduleValidService;
        this.userClient = userClient;
    }

    @GetMapping("/calendar")
    public ResponseEntity<ResponseMessage> findAllSchedule(@RequestHeader("Authorization")String token){

        int userNo = scheduleValidService.getTokenUserNo(token);
        int coupleNo = (int)userClient.findCoupleNoByUserNo(userNo).getBody().getResult().get("coupleNo");
        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);
        List<TodoCourseDTO> todoCourseList = todoCourseService.findAllTodoCourse(coupleNo);
        LocalDateTime metDay = coupleService.findCoupleByCoupleNo(coupleNo).getCoupleStartDate();

        List<CalendarDTO> calendarDTOS =
                scheduleService.makeCalendarList(coupleScheduleList,todoCourseList,metDay);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("CalendarList",calendarDTOS);

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 조회 성공",responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> findSchedule(@RequestBody UserCoupleNoVO userCoupleNoVO,
                                                           @PathVariable int scheduleNo,
                                                           @RequestHeader("Authorization")String token){
        boolean isValid = scheduleValidService.isValidUserNoAndCoupleNo(userCoupleNoVO.getUserNo(), userCoupleNoVO.getCoupleNo(), token);
        if(!isValid) {
            ResponseMessage unValidaMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 조회할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(unValidaMessage);
        }
        FindScheduleDTO findScheduleDTO = new FindScheduleDTO(userCoupleNoVO.getUserNo(),
                userCoupleNoVO.getCoupleNo(),scheduleNo);
        ScheduleDTO scheduleDTO = scheduleService.findSchedule(findScheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("schedule",scheduleDTO);

        if (scheduleDTO == null) {
            ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 조회할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 조회 성공",responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
