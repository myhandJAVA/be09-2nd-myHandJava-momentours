package com.myhandjava.momentours.diary.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.application.service.DiaryService;
import com.myhandjava.momentours.diary.command.domain.vo.RequestModifyCommentVO;
import com.myhandjava.momentours.diary.command.domain.vo.RequestModifyDiaryVO;
import com.myhandjava.momentours.diary.command.domain.vo.RequestRegistCommentVO;
import com.myhandjava.momentours.diary.command.domain.vo.RequestRegistDiaryVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                                                       @RequestAttribute("claims") Claims claims,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        Integer diaryCoupleNo = (Integer)claims.get("coupleNo");
        Integer diaryUserNo = (Integer)claims.get("userNo");
        newDiary.setFiles(files);

        DiaryDTO diaryDTO = modelMapper.map(newDiary, DiaryDTO.class);
        diaryDTO.setDiaryUserNo(diaryUserNo);
        diaryDTO.setCoupleNo(diaryCoupleNo);
        diaryService.registDiary(diaryDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newDiary", newDiary);

        ResponseMessage responseMessage = new ResponseMessage
                (201, "등록성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 일기 삭제(soft delete)
    @DeleteMapping("/{diaryNo}")
    public ResponseEntity<ResponseMessage> removeDiary(@PathVariable int diaryNo, @RequestAttribute("claims") Claims claims) {
        int diaryUserNo = (Integer)claims.get("userNo");
        diaryService.removeDiary(diaryNo, diaryUserNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 일기 수정
    @PutMapping("/{diaryNo}")
    public ResponseEntity<ResponseMessage> modifyDiary(@PathVariable int diaryNo,
                                                       @ModelAttribute RequestModifyDiaryVO modifyDiary,
                                                       @RequestAttribute("claims") Claims claims,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        int diaryUserNo = (Integer)claims.get("userNo");

        modifyDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(modifyDiary, DiaryDTO.class);
        diaryDTO.setDiaryUserNo(diaryUserNo);
        diaryService.modifyDiary(diaryDTO, diaryNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyDiary", modifyDiary);

        ResponseMessage responseMessage = new ResponseMessage(201, "수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<ResponseMessage> registComment(@RequestBody RequestRegistCommentVO newComment,
                                                         @RequestAttribute("claims") Claims claims) {

        int userNo = (Integer)claims.get("userNo");
        CommentDTO commentDTO = modelMapper.map(newComment, CommentDTO.class);
        commentDTO.setCommentUserNo(userNo);
        diaryService.registComment(commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newComment", newComment);

        ResponseMessage responseMessage = new ResponseMessage(201, "댓글등록성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentNo}")
    public ResponseEntity<ResponseMessage> removeComment(@PathVariable int commentNo, @RequestAttribute("claims") Claims claims) {
        int commentUserNo = (Integer)claims.get("userNo");
        diaryService.removeComment(commentNo, commentUserNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 댓글 수정
    @PutMapping("/comment/{commentNo}")
    public ResponseEntity<ResponseMessage> modifyComment(@PathVariable int commentNo,
                                                         @RequestBody RequestModifyCommentVO modifyComment,
                                                         @RequestAttribute("claims") Claims claims) {

        int commentUserNo = (Integer)claims.get("userNo");
        CommentDTO commentDTO = modelMapper.map(modifyComment, CommentDTO.class);
        commentDTO.setCommentUserNo(commentUserNo);
        diaryService.modifyComment(commentNo, commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyComment", modifyComment);

        ResponseMessage responseMessage = new ResponseMessage(201, "댓글 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    @PostMapping("/temporary")
    public ResponseEntity<ResponseMessage> registTempSave(@ModelAttribute RequestRegistDiaryVO newDiary,
                                                          @RequestAttribute("claims") Claims claims,
                                                          @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        int userNo = (Integer)claims.get("userNo");
        newDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(newDiary, DiaryDTO.class);
        diaryDTO.setDiaryUserNo(userNo);
        diaryService.registTempDiary(diaryDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newDiary", newDiary);

        ResponseMessage responseMessage = new ResponseMessage(201, "임시저장 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }
}
