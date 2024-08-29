package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.command.domain.repository.ScheduleRepository;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("scheduleCommandServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleServiceImpl(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void registSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        schedule.setScheduleStartDate(scheduleDTO.getScheduleStartDate());
        schedule.setScheduleEndDate(scheduleDTO.getScheduleEndDate());
        schedule.setScheduleMemo(scheduleDTO.getScheduleMemo());
        schedule.setScheduleTitle(scheduleDTO.getScheduleTitle());
        schedule.setCoupleNo(scheduleDTO.getCoupleNo());

        scheduleRepository.save(schedule);
    }
}
