package com.myhandjava.momentours.diary.query.controller;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import com.myhandjava.momentours.diary.query.service.DiaryService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Get diary", description = "해당 날짜에 맞는 일기를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("")
    public List<DiaryDTO> findDiary(@Parameter(description = "날짜", required = true, example = "2024-09-05") @RequestParam String diaryCreateDate,
                                    @Parameter(description = "커플번호", required = true, example = "1") @RequestParam int coupleNo,
                                    @Parameter(description = "회원번호", required = true, example = "1") @RequestParam int  userNo) {

        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setDiaryCreateDate(diaryCreateDate);
        diaryDTO.setCoupleNo(coupleNo);
        diaryDTO.setDiaryUserNo(userNo);

        List<DiaryDTO> list = diaryService.findDiary(diaryDTO);
        return list;
    }

    @Operation(summary = "Get diary All", description = "일기를 전체 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("/all")
    public List<DiaryDTO> findAllDiary(@Parameter(description = "커플번호", required = true, example = "1") @RequestParam int coupleNo) {

        List<DiaryDTO> list = diaryService.findAllDiary(coupleNo);

        return list;
    }
}
