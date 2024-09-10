package com.myhandjava.momentours.diary.query.service;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import com.myhandjava.momentours.diary.query.repository.DiaryMapper;
import com.myhandjava.momentours.file.query.repository.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("diaryQueryServiceImpl")
@Slf4j
public class DiaryServiceImpl implements DiaryService {

    private final DiaryMapper diaryMapper;

    @Autowired
    public DiaryServiceImpl(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

    // 일기 + 파일 + 댓글 조회
    @Override
    public List<DiaryDTO> findDiary(DiaryDTO diaryDTO) {
        List<DiaryDTO> result = diaryMapper.selectDiary(diaryDTO);

        return result;
    }

    @Override
    public List<DiaryDTO> findAllDiary(int coupleNo) {

        List<DiaryDTO> result = diaryMapper.selectAllDiary(coupleNo);

        return result;
    }

    // 일기 1번만 쓰도록 하기
    @Override
    public int findCountDiary(DiaryDTO diaryDTO) {
        int count = diaryMapper.selectCountDiary(diaryDTO);
        return count;
    }
}
