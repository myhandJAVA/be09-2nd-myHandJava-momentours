package com.myhandjava.momentours.schedule.command.application.service;

import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import com.myhandjava.momentours.schedule.command.domain.repository.ScheduleRepository;
import com.myhandjava.momentours.schedule.query.dto.ScheduleDTO;
import com.myhandjava.momentours.schedule.command.application.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ScheduleServiceImplTests {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @Transactional
    void 일정_등록_테스트(){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(-1);
        scheduleDTO.setCoupleNo(1);
        scheduleDTO.setScheduleStartDate(LocalDateTime.now());
        scheduleDTO.setScheduleEndDate(LocalDateTime.now());
        scheduleDTO.setScheduleTitle("테스트 제목");
        scheduleDTO.setScheduleMemo("테스트 메모");

        scheduleService.registSchedule(scheduleDTO);

        Assertions.assertNotNull(scheduleRepository.findById(-1));


    }

}