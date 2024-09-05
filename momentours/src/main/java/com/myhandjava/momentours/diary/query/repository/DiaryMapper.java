package com.myhandjava.momentours.diary.query.repository;

import com.myhandjava.momentours.diary.query.dto.DiaryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    // 일기조회
    List<DiaryDTO> selectDiary(DiaryDTO diaryDTO);

    List<DiaryDTO> selectAllDiary(int coupleNo);
}
