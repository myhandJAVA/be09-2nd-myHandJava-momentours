package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;

import java.io.IOException;

public interface DiaryService {

    void registDiary(DiaryDTO newDiary);

    void removeDiary(int diaryNo, int userNo);

    void modifyDiary(DiaryDTO diaryDTO, int userNo, int diaryNo) throws IOException;
}
