package com.myhandjava.momentours.diary.command.application.service;


import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;

public interface CommentService {
    void registComment(CommentDTO commentDTO);

    void removeComment(int commentNo, int commentUserNo);

    void modifyComment(int commentNo, CommentDTO commentDTO);
}
