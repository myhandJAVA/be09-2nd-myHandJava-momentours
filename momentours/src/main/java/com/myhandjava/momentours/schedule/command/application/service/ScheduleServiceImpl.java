package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service("scheduleCommandServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

    @PostMapping("/calendar/{coupleNo}")
    public List<ScheduleDTO> registSchedule(@PathVariable int coupleNo, ScheduleDTO scheduleDTO){
        return null;
    }
}
