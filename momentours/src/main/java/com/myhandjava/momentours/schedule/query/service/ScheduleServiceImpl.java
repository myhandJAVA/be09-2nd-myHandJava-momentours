package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.CalendarDTO;
import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.repository.ScheduleMapper;
import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("scheduleQueryServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleMapper scheduleMapper;

    @Autowired
    ScheduleServiceImpl(ScheduleMapper scheduleMapper){
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<ScheduleDTO> findAllScheduleByCoupleNo(int coupleNo) {
        List<Schedule> schedules =  scheduleMapper.findAllScheduleByCoupleNo(coupleNo);
        List<ScheduleDTO> scheduleDTOList = schedules.stream().map(
                schedule ->
                new ScheduleDTO(schedule.getCoupleNo(),schedule.getScheduleStartDate(),
                        schedule.getScheduleEndDate(),schedule.getScheduleTitle(),
                        schedule.getScheduleMemo(),schedule.getScheduleNo())
        ).collect(Collectors.toList());

        return scheduleDTOList;

    }
    @Override
    public ScheduleDTO findSchedule(FindScheduleDTO findScheduleDTO){
        Schedule schedule = scheduleMapper.findSchedule(findScheduleDTO);

        if(schedule == null) return null;

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(schedule.getScheduleNo());
        scheduleDTO.setScheduleTitle(schedule.getScheduleTitle());
        scheduleDTO.setScheduleMemo(schedule.getScheduleMemo());
        scheduleDTO.setScheduleStartDate(schedule.getScheduleStartDate());
        scheduleDTO.setScheduleEndDate(schedule.getScheduleEndDate());
        scheduleDTO.setCoupleNo(schedule.getCoupleNo());

        return scheduleDTO;
    }

    @Override
    public List<CalendarDTO> makeCalendarList(List<ScheduleDTO> coupleScheduleList,
                                              List<TodoCourseDTO> todoCourseList,
                                              LocalDateTime metDay) {
        List<CalendarDTO> result = new ArrayList<>();
        for(ScheduleDTO schedule:coupleScheduleList){
            CalendarDTO calendar = scheduleToCalendar(schedule);
            result.add(calendar);
        }
        for(TodoCourseDTO todoCourse: todoCourseList){
            CalendarDTO calendar = todoCourseToCalendar(todoCourse);
            result.add(calendar);
        }

        for(int i=0; i<10; i++){
            String title = Integer.toString(i*100) +'일';
            String content = title;
            LocalDateTime start = metDay.plusDays(i*100);
            LocalDateTime end = start;
            String contentType = "schedule";
            CalendarDTO calendar = new CalendarDTO();
            calendar.setTitle(title);
            calendar.setContent(content);
            calendar.setStart(start);
            calendar.setEnd(end);
            calendar.setContentType(contentType);
            result.add(calendar);
        }
        for (int j=0; j<10; j++){
            String title = j + "년";
            String content = title;
            LocalDateTime start = metDay.plusYears(j);
            LocalDateTime end = start;
            String contentType = "schedule";
            CalendarDTO calendar = new CalendarDTO();
            calendar.setTitle(title);
            calendar.setContent(content);
            calendar.setStart(start);
            calendar.setEnd(end);
            calendar.setContentType(contentType);
            result.add(calendar);
        }

        return result;
    }

    private CalendarDTO scheduleToCalendar(ScheduleDTO schedule){
        CalendarDTO calendar = new CalendarDTO();
        calendar.setStart(schedule.getScheduleStartDate());
        calendar.setEnd(schedule.getScheduleEndDate());
        calendar.setTitle(schedule.getScheduleTitle());
        calendar.setContent(schedule.getScheduleMemo());
        calendar.setContentType("schedule");
        return calendar;
    }

    private CalendarDTO todoCourseToCalendar(TodoCourseDTO todoCourse){
        CalendarDTO calendar = new CalendarDTO();
        calendar.setStart(todoCourse.getToDoCourseStartDate());
        calendar.setEnd(todoCourse.getToDoCourseEndDate());
        calendar.setTitle(todoCourse.getToDoCourseTitle());
        calendar.setContent(todoCourse.getToDoCourseMemo());
        calendar.setContentType("todocourse");
        return calendar;
    }
}
