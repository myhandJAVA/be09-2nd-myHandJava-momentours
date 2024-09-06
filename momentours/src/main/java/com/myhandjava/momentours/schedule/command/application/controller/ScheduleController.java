package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.command.domain.vo.ModifyScheduleVO;
import com.myhandjava.momentours.schedule.command.domain.vo.RegistScheduleVO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController("ScheduleCommandController")
@RequestMapping("calendar")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage> registSchedule(@RequestBody RegistScheduleVO registScheduleVO,
                                                          @RequestAttribute("claims") Claims claims){

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setCoupleNo((Integer)claims.get("coupleNo"));
        scheduleDTO.setScheduleStartDate(registScheduleVO.getScheduleStartDate());
        scheduleDTO.setScheduleEndDate(registScheduleVO.getScheduleEndDate());
        scheduleDTO.setScheduleTitle(registScheduleVO.getScheduleTitle());
        scheduleDTO.setScheduleMemo(registScheduleVO.getScheduleMemo());

        scheduleService.registSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(201,"일정 생성 성공", responseMap );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PutMapping("/{scheduleNo}")
    public ResponseEntity<ResponseMessage> modifySchedule(@RequestBody ModifyScheduleVO modifyScheduleVO,
                                                          @PathVariable int scheduleNo,
                                                          @RequestAttribute("claims") Claims claims){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setCoupleNo((Integer)claims.get("coupleNo"));
        scheduleDTO.setScheduleStartDate(modifyScheduleVO.getScheduleStartDate());
        scheduleDTO.setScheduleEndDate(modifyScheduleVO.getScheduleEndDate());
        scheduleDTO.setScheduleTitle(modifyScheduleVO.getScheduleTitle());
        scheduleDTO.setScheduleMemo(modifyScheduleVO.getScheduleMemo());
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleService.modifySchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","localhost:8000/calendar");

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 수정 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{scheduleNo}")
    public ResponseEntity<ResponseMessage> removeSchedule(@PathVariable int scheduleNo,
                                                          @RequestAttribute("claims") Claims claims){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        Integer coupleNo = (Integer)claims.get("coupleNo");

        if (coupleNo == null) {
            ResponseMessage responseMessage = new ResponseMessage(500,"커플번호가 존재안해요",null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.removeSchedule(scheduleDTO);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("removedSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(200,"일정 삭제 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
