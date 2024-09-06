package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    void registSchedule(ScheduleDTO scheduleDTO);

    void modifySchedule(ScheduleDTO scheduleDTO);

    void removeSchedule(ScheduleDTO scheduleDTO);
}
