package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController("ScheduleCommandController")
public class ScheduleController {
    private ScheduleService scheduleService;
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/calendar/{coupleNo}")
    public ResponseEntity<ResponseMessage> registSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){

        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.registSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/"+ coupleNo);

        ResponseMessage responseMessage = new ResponseMessage(201,"일정 생성 성공", responseMap );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PutMapping("/calendar/{coupleNo}")
    public ResponseEntity<ResponseMessage> updateSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.updateSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/"+ coupleNo);

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 수정 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/calendar/{coupleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.deleteSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("deletedSchedule","/calendar/"+ coupleNo);

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 삭제 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
