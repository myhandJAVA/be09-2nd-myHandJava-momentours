package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.common.HttpStatusCode;
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
    private final ScheduleService scheduleService;
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/calendar")
    public ResponseEntity<ResponseMessage> registSchedule(@RequestBody ScheduleDTO scheduleDTO){
        scheduleService.registSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("url","/calendar");

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.CREATED.getCode(), "일정 생성 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> updateSchedule(@RequestBody ScheduleDTO scheduleDTO,
                                                          @PathVariable int scheduleNo,
                                                          @RequestBody int coupleNo){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleService.updateSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 수정 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable int scheduleNo,
                                                          @RequestBody int coupleNo){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.deleteSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("deletedSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 삭제 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
