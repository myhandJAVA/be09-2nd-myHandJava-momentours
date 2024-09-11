package com.myhandjava.momentours.schedule.query.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ScheduleServiceImplTests {
    @Autowired
    ScheduleService scheduleService;

    @Test
    public void 커플_번호로_일정_조회(){
        int coupleNo = 1;
        List<ScheduleDTO> coupleScheduleList = scheduleService.findAllScheduleByCoupleNo(coupleNo);
        assertNotNull(coupleScheduleList);
        assertEquals(1,coupleScheduleList.get(0).getCoupleNo());

    }

}