package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTests {

    @Autowired
    private DiaryService diaryService;

    @DisplayName("일기 등록 확인 테스트")
    @Test
    @Transactional
    void registDiary() {

        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryContent("오늘은 열심히 프로젝트를 했다. 일기 등록을 끝내고 싶다.");
        diaryDTO.setDiaryCreateDate(LocalDateTime.now());
        diaryDTO.setDiaryUserNo(1);
        diaryDTO.setCoupleNo(1);

        Assertions.assertDoesNotThrow(
                () -> diaryService.registDiary(diaryDTO)
        );
    }

    @DisplayName("일기 삭제 확인 테스트")
    @Test
    @Transactional
    void removeDiary() {

        Assertions.assertDoesNotThrow(
                () -> diaryService.removeDiary(7, 2)
        );
    }

}