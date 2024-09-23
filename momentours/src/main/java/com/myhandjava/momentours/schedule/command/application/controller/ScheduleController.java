package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.command.domain.service.ScheduleValidService;
import com.myhandjava.momentours.schedule.command.domain.vo.PutScheduleVO;
import com.myhandjava.momentours.schedule.command.domain.vo.UserCoupleNoVO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController("ScheduleCommandController")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleValidService scheduleValidService;
    private ScheduleController(ScheduleService scheduleService,
                               ScheduleValidService scheduleValidService){
        this.scheduleService = scheduleService;
        this.scheduleValidService = scheduleValidService;
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
    public ResponseEntity<ResponseMessage> updateSchedule(@RequestBody PutScheduleVO putScheduleVO,
                                                          @PathVariable int scheduleNo,
                                                          @RequestHeader("Authorization") String token){
        boolean isValidRequest = scheduleValidService
                .isValidUserNoAndCoupleNo(putScheduleVO.getUserNo()
                        ,putScheduleVO.getCoupleNo()
                        ,token);
        if(!isValidRequest) {
            ResponseMessage unValidaMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 수정할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(unValidaMessage);
        }
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleDTO.setCoupleNo(putScheduleVO.getCoupleNo());
        scheduleDTO.setScheduleTitle(putScheduleVO.getScheduleTitle());
        scheduleDTO.setScheduleMemo(putScheduleVO.getScheduleMemo());
        scheduleDTO.setScheduleStartDate(putScheduleVO.getScheduleStartDate());
        scheduleDTO.setScheduleEndDate(putScheduleVO.getScheduleEndDate());
        boolean isCompleted = scheduleService.updateSchedule(scheduleDTO);

        if(!isCompleted) {
            ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(),"해당 정보를 수정할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("newSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 수정 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/calendar/{scheduleNo}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@RequestBody UserCoupleNoVO userCoupleNoVO,
                                                          @PathVariable int scheduleNo,
                                                          @RequestHeader("Authorization") String token){
        boolean isValidRequest = scheduleValidService
                .isValidUserNoAndCoupleNo(userCoupleNoVO.getUserNo()
                        ,userCoupleNoVO.getCoupleNo()
                        ,token);
        if(!isValidRequest) {
            ResponseMessage unValidaMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 삭제할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(unValidaMessage);
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(scheduleNo);
        scheduleDTO.setCoupleNo(userCoupleNoVO.getCoupleNo());
        boolean isCompleted = scheduleService.deleteSchedule(scheduleDTO);

        if(!isCompleted){
            ResponseMessage unValidaMessage =
                    new ResponseMessage(HttpStatusCode.FORBIDDEN.getCode(), "해당 정보를 삭제할 권한이 없습니다.",null);
            return ResponseEntity.status(HttpStatus.OK).body(unValidaMessage);
        }

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("deletedSchedule","/calendar/");

        ResponseMessage responseMessage = new ResponseMessage(HttpStatusCode.OK.getCode(),"일정 삭제 성공", responseMap );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
