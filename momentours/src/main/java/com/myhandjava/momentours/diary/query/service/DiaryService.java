package com.myhandjava.momentours.diary.query.service;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;

import java.util.List;

public interface DiaryService {

    List<DiaryDTO> findDiary(DiaryDTO diaryDTO);

    List<DiaryDTO> findAllDiary(int coupleNo);

    int findCountDiary(DiaryDTO diaryDTO);
}
