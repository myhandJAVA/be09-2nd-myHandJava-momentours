package com.myhandjava.momentours.diary.query.controller;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import com.myhandjava.momentours.diary.query.service.DiaryService;
import io.jsonwebtoken.Claims;
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


    @GetMapping("")
    public List<DiaryDTO> findDiary(@RequestParam String diaryCreateDate, @RequestAttribute("coupleNo") Claims coupleNo) {

        int diaryCoupleNo = Integer.parseInt(coupleNo.getAudience());
        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryCreateDate(diaryCreateDate);

        diaryDTO.setCoupleNo(diaryCoupleNo);
        List<DiaryDTO> list = diaryService.selectDiary(diaryDTO);

        return list;
    }

    @GetMapping("/all")
    public List<DiaryDTO> findAllDiary(@RequestAttribute("coupleNo") Claims coupleNo) {

        int diaryCoupleNo = Integer.parseInt(coupleNo.getAudience());
        List<DiaryDTO> list = diaryService.findAllDiary(diaryCoupleNo);

        return list;
    }
}
