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

    @Test
    @Transactional
    void 일정_수정_테스트(){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(1);
        scheduleDTO.setCoupleNo(1);
        scheduleDTO.setScheduleStartDate(LocalDateTime.now());
        scheduleDTO.setScheduleEndDate(LocalDateTime.now());
        scheduleDTO.setScheduleTitle("테스트 제목");
        scheduleDTO.setScheduleMemo("테스트 메모");

        Schedule oldSchedule = scheduleRepository.findById(1).orElseThrow();

        String oldTitle = oldSchedule.getScheduleTitle();
        String oldMemo = oldSchedule.getScheduleMemo();

        scheduleService.modifySchedule(scheduleDTO);
        scheduleRepository.flush();

        Schedule newSchedule = scheduleRepository.findById(1).orElseThrow();
        String newTitle = newSchedule.getScheduleTitle();
        String newMemo = newSchedule.getScheduleMemo();

        Assertions.assertNotEquals(oldTitle,newTitle);
        Assertions.assertNotEquals(oldMemo,newMemo);

    }

    @Transactional
    @Test
    void 일정_삭제_테스트(){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleNo(1);

        Schedule notDeletedSchedule = scheduleRepository.findById(scheduleDTO.getScheduleNo()).orElseThrow();
        int oldScheduleIsDeleted = notDeletedSchedule.getScheduleIsDeleted();

        scheduleService.removeSchedule(scheduleDTO);

        Schedule deletedSchedule = scheduleRepository.findById(scheduleDTO.getScheduleNo()).orElseThrow();
        int newScheduleIsDeleted = deletedSchedule.getScheduleIsDeleted();

        Assertions.assertNotEquals(oldScheduleIsDeleted,newScheduleIsDeleted);
        Assertions.assertEquals(1,newScheduleIsDeleted);
    }

}