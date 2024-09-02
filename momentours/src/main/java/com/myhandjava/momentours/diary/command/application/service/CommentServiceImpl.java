package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Comment;
import com.myhandjava.momentours.diary.command.domain.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    // 댓글 등록
    @Override
    @Transactional
    public void registComment(CommentDTO commentDTO) {
        commentDTO.setCommentCreateDate(LocalDateTime.now());
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        commentRepository.save(comment);
    }
}
