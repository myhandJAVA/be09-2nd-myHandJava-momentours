package com.myhandjava.momentours.schedule.query.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.service.ScheduleService;
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

    @Autowired
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping("/calendar/{coupleNo}")
    public ResponseEntity<ResponseMessage> findAllSchedule(@PathVariable int coupleNo){

        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("ScheduleList",coupleScheduleList);

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 조회 성공",responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
