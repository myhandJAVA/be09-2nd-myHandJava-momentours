package com.myhandjava.momentours.diary.query.controller;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import com.myhandjava.momentours.diary.query.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }


    @GetMapping("/all")
    public String findDiary(@RequestParam String diaryCreateDate, @RequestParam int coupleNo) {

        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryCreateDate(diaryCreateDate);

        diaryDTO.setCoupleNo(coupleNo);
        List<DiaryDTO> list = diaryService.selectDiary(diaryDTO);

        return list.toString();
    }
}
