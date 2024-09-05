package com.myhandjava.momentours.file.command.application.service;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<FileEntity> saveFileDiary(List<MultipartFile> files, Diary diary) throws IOException;

    void updateFileDiaryIsDeleted(Diary diary);
}
