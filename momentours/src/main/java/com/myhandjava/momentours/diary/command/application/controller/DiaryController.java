package com.myhandjava.momentours.diary.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.application.service.DiaryService;
import com.myhandjava.momentours.diary.command.domain.vo.RequestModifyDiaryVO;
import com.myhandjava.momentours.diary.command.domain.vo.RequestRegistDiaryVO;
import com.myhandjava.momentours.file.command.application.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(value = "diaryCommandController")
@RequestMapping("/diary")
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public DiaryController(DiaryService diaryService, ModelMapper modelMapper) {
        this.diaryService = diaryService;
        this.modelMapper = modelMapper;
    }

    // 일기 등록
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registDiary(@ModelAttribute RequestRegistDiaryVO newDiary,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        newDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(newDiary, DiaryDTO.class);
        diaryService.registDiary(diaryDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newDiary", newDiary);

        ResponseMessage responseMessage = new ResponseMessage(201, "등록성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 일기 삭제(soft delete)
    @DeleteMapping("/{diaryNo}")
    public ResponseEntity<?> removeDiary(@PathVariable int diaryNo, @RequestParam int userNo) {

        diaryService.removeDiary(diaryNo, userNo);


        return ResponseEntity
                .noContent()
                .build();
    }

    // 일기 수정
    @PutMapping("/{diaryNo}/{userNo}")
    public ResponseEntity<ResponseMessage> modifyDiary(@PathVariable int diaryNo,
                                                       @ModelAttribute RequestModifyDiaryVO modifyDiary,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                                       @PathVariable int userNo) throws IOException {

        modifyDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(modifyDiary, DiaryDTO.class);
        diaryService.modifyDiary(diaryDTO, userNo, diaryNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyDiary", modifyDiary);

        ResponseMessage responseMessage = new ResponseMessage(201, "수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }
}
