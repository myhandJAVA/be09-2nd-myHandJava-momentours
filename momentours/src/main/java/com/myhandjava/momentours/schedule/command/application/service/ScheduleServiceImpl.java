package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.command.domain.repository.ScheduleRepository;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scheduleCommandServiceImpl")
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

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

    @Transactional
    public boolean updateSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheduleNo()).orElseThrow();
        if(scheduleDTO.getCoupleNo() != schedule.getCoupleNo()) return false;
        if(schedule.getScheduleIsDeleted()!=0) return false;
        schedule.setScheduleStartDate(scheduleDTO.getScheduleStartDate());
        schedule.setScheduleEndDate(scheduleDTO.getScheduleEndDate());
        schedule.setScheduleMemo(scheduleDTO.getScheduleMemo());
        schedule.setScheduleTitle(scheduleDTO.getScheduleTitle());
        schedule.setCoupleNo(scheduleDTO.getCoupleNo());

        scheduleRepository.save(schedule);
        return true;
    }

    @Transactional
    public boolean deleteSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheduleNo()).orElseThrow();
        if(scheduleDTO.getCoupleNo() != schedule.getCoupleNo()) return false;

        schedule.setScheduleIsDeleted(1);

        scheduleRepository.save(schedule);
        return true;
    }
}
