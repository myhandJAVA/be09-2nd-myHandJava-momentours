package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.query.dto.CalendarDTO;
import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllScheduleByCoupleNo(int coupleNo);

    ScheduleDTO findSchedule(FindScheduleDTO scheduleNo);

    List<CalendarDTO> makeCalendarList(List<ScheduleDTO> coupleScheduleList, List<TodoCourseDTO> todoCourseList, LocalDateTime metDay);
}
