package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
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
}
