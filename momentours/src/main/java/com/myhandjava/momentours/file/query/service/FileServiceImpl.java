package com.myhandjava.momentours.file.query.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.file.query.dto.FileDTO;
import com.myhandjava.momentours.file.query.repository.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fileQueryServiceImpl")
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // 일기 번호로 파일 조회
    @Override
    public List<FileDTO> findFilesByDiaryNo(int diaryNo) {
        List<FileDTO> result = fileMapper.selectFilesByDiaryNo(diaryNo);
        if(result == null || result.isEmpty()) {
            throw new CommonException(HttpStatusCode.NOT_FOUND_FILE);
        }
        return result;
    }
}
