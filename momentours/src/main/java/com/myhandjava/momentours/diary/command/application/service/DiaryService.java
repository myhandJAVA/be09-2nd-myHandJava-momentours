package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.CommentDTO;
import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;

import java.io.IOException;

public interface DiaryService {

    void registDiary(DiaryDTO newDiary) throws IOException;

    void removeDiary(int diaryNo, int userNo);

    void modifyDiary(DiaryDTO diaryDTO, int userNo, int diaryNo) throws IOException;

    void registComment(CommentDTO commentDTO);

    void removeComment(int commentNo, int commentUserNo);

    void modifyComment(int commentNo, CommentDTO commentDTO);

    void registTempDiary(DiaryDTO diaryDTO);

    void removeAllDiary(int coupleNo);
}
