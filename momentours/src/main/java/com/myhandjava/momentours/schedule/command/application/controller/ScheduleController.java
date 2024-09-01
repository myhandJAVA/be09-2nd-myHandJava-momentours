package com.myhandjava.momentours.schedule.command.application.controller;

import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ScheduleCommandController")
public class ScheduleController {
    private ScheduleService scheduleService;
    private ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping("/calendar/{coupleNo}")
    public String registSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.registSchedule(scheduleDTO);
        return "redirect:/calendar/" + coupleNo;
    }

    @PutMapping("/calendar/{coupleNo}")
    public String updateSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.updateSchedule(scheduleDTO);
        return "redirect:/calendar/"+coupleNo;
    }

    @DeleteMapping("/calendar/{coupleNo}")
    public String deleteSchedule(@PathVariable int coupleNo, @RequestBody ScheduleDTO scheduleDTO){
        scheduleDTO.setCoupleNo(coupleNo);
        scheduleService.deleteSchedule(scheduleDTO);
        return "redirect:/calendar/" + coupleNo;
    }
}
