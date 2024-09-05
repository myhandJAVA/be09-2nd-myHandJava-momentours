package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAllScheduleByCoupleNo(int coupleNo);
}
