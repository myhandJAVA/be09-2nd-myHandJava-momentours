package com.myhandjava.momentours.file.query.repository;

import com.myhandjava.momentours.file.query.dto.FileDTO;
import jakarta.persistence.LockModeType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

@Mapper
public interface FileMapper {

    List<FileDTO> selectFilesByDiaryNo(int diaryNo);
}
