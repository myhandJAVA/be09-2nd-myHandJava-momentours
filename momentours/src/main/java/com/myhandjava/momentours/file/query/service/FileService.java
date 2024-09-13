package com.myhandjava.momentours.file.query.service;

import com.myhandjava.momentours.file.query.dto.FileDTO;

import java.util.List;

public interface FileService {
    List<FileDTO> findFilesByDiaryNo(int diaryNo);
}
