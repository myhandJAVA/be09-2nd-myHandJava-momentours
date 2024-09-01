package com.myhandjava.momentours.file.query.repository;

import com.myhandjava.momentours.file.query.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    List<FileDTO> selectFilesByDiaryNo(int diaryNo);
}
