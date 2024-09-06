package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.PathParam;
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

    @PostMapping("/calendar")
    public ResponseEntity<ResponseMessage> registSchedule(@RequestBody ScheduleDTO scheduleDTO,
                                                          @RequestAttribute("claims") Claims claims){
        Integer coupleNo = (Integer)claims.get("coupleNo");
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.registSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(201,"일정 생성 성공", responseMap );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PutMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> updateSchedule(@RequestBody ScheduleDTO scheduleDTO,
                                                          @PathVariable int scheduleNo,
                                                          @RequestAttribute("claims") Claims claims){
        Integer coupleNo = (Integer)claims.get("coupleNo");
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleService.updateSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 수정 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable int scheduleNo,
                                                          @RequestAttribute("claims") Claims claims){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        Integer coupleNo = (Integer)claims.get("coupleNo");
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.deleteSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("deletedSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 삭제 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
