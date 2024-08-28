package com.myhandjava.momentours.diary.query.service;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import com.myhandjava.momentours.diary.query.repository.DiaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService {

    private final DiaryMapper diaryMapper;

    @Autowired
    public DiaryServiceImpl(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

    // 일기 조회
    @Override
    public List<DiaryDTO> selectDiary(DiaryDTO diaryDTO) {
        List<DiaryDTO> result = diaryMapper.selectDiary(diaryDTO);

        return result;
    }

}
