package com.myhandjava.momentours.diary.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.service.CommentService;
import com.myhandjava.momentours.diary.command.domain.vo.RequestModifyCommentVO;
import com.myhandjava.momentours.diary.command.domain.vo.RequestRegistCommentVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    // 댓글 등록
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registComment(@RequestBody RequestRegistCommentVO newComment) {

        CommentDTO commentDTO = modelMapper.map(newComment, CommentDTO.class);
        commentService.registComment(commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newComment", newComment);

        ResponseMessage responseMessage = new ResponseMessage(201, "댓글등록성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<ResponseMessage> removeComment(@PathVariable int commentNo, @RequestParam int commentUserNo) {

        commentService.removeComment(commentNo, commentUserNo);

        return ResponseEntity
                .noContent()
                .build();
    }

    // 댓글 수정
    @PutMapping("/{commentNo}")
    public ResponseEntity<ResponseMessage> modifyComment(@PathVariable int commentNo,
                                                         @RequestBody RequestModifyCommentVO modifyComment) {

        CommentDTO commentDTO = modelMapper.map(modifyComment, CommentDTO.class);
        commentService.modifyComment(commentNo, commentDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyComment", modifyComment);

        ResponseMessage responseMessage = new ResponseMessage(201, "댓글 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }
}
