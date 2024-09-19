package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllScheduleByCoupleNo(int coupleNo);

    ScheduleDTO findSchedule(FindScheduleDTO scheduleNo);
}
