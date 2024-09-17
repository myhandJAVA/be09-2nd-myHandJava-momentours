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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Post diary", description = "일기를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "일기 등록 성공!",
                content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registDiary(@ModelAttribute RequestRegistDiaryVO newDiary,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        newDiary.setFiles(files);

        DiaryDTO diaryDTO = modelMapper.map(newDiary, DiaryDTO.class);
        diaryService.registDiary(diaryDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newDiary", newDiary);

        ResponseMessage responseMessage = new ResponseMessage
                (201, "일기 등록 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 일기 삭제(soft delete)
    @Operation(summary = "Delete diary", description = "일기를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "일기 삭제 성공!"),
            @ApiResponse(responseCode = "40402", description = "해당 일기가 존재하지 않습니다.")
    })
    @DeleteMapping("/{diaryNo}")
    public ResponseEntity<ResponseMessage> removeDiary(@Parameter(description = "삭제할 일기 번호", required = true, example = "1") @PathVariable int diaryNo,
                                                       @RequestBody int diaryUserNo) {
        diaryService.removeDiary(diaryNo, diaryUserNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 일기 수정
    @Operation(summary = "Put diary", description = "일기를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일기 수정 성공!",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "40402", description = "해당 일기가 존재하지 않습니다.")
    })
    @PutMapping("/{diaryNo}")
    public ResponseEntity<ResponseMessage> modifyDiary(@Parameter(description = "수정할 일기 번호", required = true, example = "1") @PathVariable int diaryNo,
                                                       @ModelAttribute RequestModifyDiaryVO modifyDiary,
                                                       @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        modifyDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(modifyDiary, DiaryDTO.class);
        diaryService.modifyDiary(diaryDTO, diaryNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyDiary", modifyDiary);

        ResponseMessage responseMessage = new ResponseMessage(200, "일기 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    // 댓글 등록
    @Operation(summary = "Post comment", description = "일기에 댓글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공!",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("/comment")
    public ResponseEntity<ResponseMessage> registComment(@RequestBody RequestRegistCommentVO newComment) {

        CommentDTO commentDTO = modelMapper.map(newComment, CommentDTO.class);
        diaryService.registComment(commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newComment", newComment);

        ResponseMessage responseMessage = new ResponseMessage(201, "댓글 등록 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 댓글 삭제
    @Operation(summary = "Delete comment", description = "일기에 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공!"),
            @ApiResponse(responseCode = "404021", description = "해당 댓글이 존재하지 않습니다.")
    })
    @DeleteMapping("/comment/{commentNo}")
    public ResponseEntity<ResponseMessage> removeComment(@Parameter(description = "삭제할 댓글 번호", required = true, example = "1") @PathVariable int commentNo,
                                                         @RequestBody int commentUserNo) {
        diaryService.removeComment(commentNo, commentUserNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 댓글 수정
    @Operation(summary = "Put comment", description = "일기에 댓글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공!",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "404021", description = "해당 댓글이 존재하지 않습니다.")
    })
    @PutMapping("/comment/{commentNo}")
    public ResponseEntity<ResponseMessage> modifyComment(@Parameter(description = "수정할 댓글 번호", required = true, example = "1") @PathVariable int commentNo,
                                                         @RequestBody RequestModifyCommentVO modifyComment) {

        CommentDTO commentDTO = modelMapper.map(modifyComment, CommentDTO.class);
        diaryService.modifyComment(commentNo, commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyComment", modifyComment);

        ResponseMessage responseMessage = new ResponseMessage(200, "댓글 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    // 일기 임시저장
    @Operation(summary = "Post temporary", description = "일기를 임시저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "임시저장 성공!",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("/temporary")
    public ResponseEntity<ResponseMessage> registTempSave(@ModelAttribute RequestRegistDiaryVO newDiary,
                                                          @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        newDiary.setFiles(files);
        DiaryDTO diaryDTO = modelMapper.map(newDiary, DiaryDTO.class);
        diaryService.registTempDiary(diaryDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newDiary", newDiary);

        ResponseMessage responseMessage = new ResponseMessage(201, "임시저장 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }
}
