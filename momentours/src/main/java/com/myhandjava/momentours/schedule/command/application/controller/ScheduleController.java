package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ScheduleCommandController")
public class ScheduleController {
    private ScheduleService scheduleService;
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/calendar/{coupleNo}")
    public String registSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        System.out.println("dddd= " + scheduleDTO);
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.registSchedule(scheduleDTO);
        return "redirect:/calendar/" + coupleNo;

    }
}
