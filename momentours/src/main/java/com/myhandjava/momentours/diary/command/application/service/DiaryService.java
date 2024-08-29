package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;

public interface DiaryService {

    void registDiary(DiaryDTO newDiary);

    void removeDiary(int diaryNo, int userNo);
}
