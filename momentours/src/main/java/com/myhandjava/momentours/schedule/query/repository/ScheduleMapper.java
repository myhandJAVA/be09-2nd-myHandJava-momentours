package com.myhandjava.momentours.schedule.query.repository;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.FindScheduleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    List<Schedule> findAllScheduleByCoupleNo(int coupleNo);

    Schedule findSchedule(FindScheduleDTO findScheduleDTO);
}

