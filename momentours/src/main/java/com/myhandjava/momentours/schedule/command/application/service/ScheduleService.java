package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;

public interface ScheduleService {
    void registSchedule(ScheduleDTO scheduleDTO);

    boolean updateSchedule(ScheduleDTO scheduleDTO);

    boolean deleteSchedule(ScheduleDTO scheduleDTO);
}
