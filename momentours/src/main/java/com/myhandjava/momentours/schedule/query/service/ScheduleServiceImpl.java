package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.query.repository.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("scheduleQueryServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

    ScheduleMapper scheduleMapper;

    @Autowired
    ScheduleServiceImpl(ScheduleMapper scheduleMapper){
        this.scheduleMapper = scheduleMapper;
    }

    public List<ScheduleDTO> findAllScheduleByCoupleNo(int coupleNo) {
        List<Schedule> schedules =  scheduleMapper.findAllScheduleByCoupleNo(coupleNo);

        List<ScheduleDTO> scheduleDTOList = schedules.stream().map((schedule ->
                new ScheduleDTO(schedule.getScheduleNo(),schedule.getScheduleStartDate(),
                        schedule.getScheduleEndDate(),schedule.getScheduleTitle(),
                        schedule.getScheduleMemo(),schedule.getCoupleNo()))).collect(Collectors.toList());

        return scheduleDTOList;

    }
}
