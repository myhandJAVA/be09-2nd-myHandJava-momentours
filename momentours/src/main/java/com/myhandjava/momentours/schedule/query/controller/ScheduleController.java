package com.myhandjava.momentours.schedule.query.controller;

import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    ScheduleService scheduleService;

    @Autowired
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping("/calendar/{coupleNo}")
    public List<ScheduleDTO> findAllSchedule(@PathVariable int coupleNo){
        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);
        return coupleScheduleList;
    }

}
