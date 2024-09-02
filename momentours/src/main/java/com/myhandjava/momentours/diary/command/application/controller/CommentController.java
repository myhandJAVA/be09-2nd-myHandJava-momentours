package com.myhandjava.momentours.diary.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.service.CommentService;
import com.myhandjava.momentours.diary.command.domain.vo.RequestRegistCommentVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
