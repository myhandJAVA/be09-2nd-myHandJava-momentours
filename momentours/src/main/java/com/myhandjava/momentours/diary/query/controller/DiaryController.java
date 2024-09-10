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
    public List<DiaryDTO> findDiary(@RequestParam String diaryCreateDate, @RequestAttribute("claims") Claims claims) {

        int coupleNo = (Integer)claims.get("coupleNo");
        int userNo = (Integer)claims.get("userNo");
        DiaryDTO diaryDTO = new DiaryDTO();

        diaryDTO.setDiaryCreateDate(diaryCreateDate);
        diaryDTO.setCoupleNo(coupleNo);
        diaryDTO.setDiaryUserNo(userNo);

        List<DiaryDTO> list = diaryService.findDiary(diaryDTO);
        return list;
    }

    @GetMapping("/all")
    public List<DiaryDTO> findAllDiary(@RequestAttribute("claims") Claims claims) {

        int coupleNo = (Integer)claims.get("coupleNo");
        List<DiaryDTO> list = diaryService.findAllDiary(coupleNo);

        return list;
    }
}
