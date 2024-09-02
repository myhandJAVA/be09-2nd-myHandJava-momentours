package com.myhandjava.momentours.diary.query.service;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DiaryServiceImplTests {

    @Autowired
    private DiaryService diaryService;


    @DisplayName("일기 조회 확인 테스트")
    @Test
    void testFindAllDiary() {
        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryCreateDate("2024-01-02");
        diaryDTO.setCoupleNo(2);

        List<DiaryDTO> result = diaryService.selectDiary(diaryDTO);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        result.forEach(System.out::println);
    }
}