package com.myhandjava.momentours.file.command.application.service;

import com.myhandjava.momentours.common.CommonException;
import com.myhandjava.momentours.common.HttpStatusCode;
import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.file.command.domain.aggregate.FileBoardSort;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import com.myhandjava.momentours.file.command.domain.repository.FileRepository;
import com.myhandjava.momentours.file.query.dto.FileDTO;
import com.myhandjava.momentours.file.query.repository.FileMapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final ResourceLoader resourceLoader;
    private final ModelMapper modelMapper;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository,
                           FileMapper fileMapper,
                           ResourceLoader resourceLoader,
                           ModelMapper modelMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.resourceLoader = resourceLoader;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<FileEntity> saveFileDiary(List<MultipartFile> files, Diary diary) throws IOException {
        if(files.isEmpty()) {
            throw new CommonException(HttpStatusCode.NOT_FOUND_FILE);
        }

        List<FileEntity> savedFiles = new ArrayList<>();

        // Classpath 위치에서 실제 디렉토리 경로 얻기
        String filePath = resourceLoader.getResource("classpath:static/uploadFiles/img/diary")
                .getFile()
                .getAbsolutePath();

        for (int i = 0; i < files.size(); i++) {
            String originalFilename = files.get(i).getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String saveName = UUID.randomUUID().toString().replace("-", "") + extension;

            FileEntity diaryFile = new FileEntity();
            diaryFile.setFileOriginalName(originalFilename);
            diaryFile.setFileExtension(extension);
            diaryFile.setFileSaveName(saveName);
            diaryFile.setFileExtension(extension);
            diaryFile.setFileDirectory(filePath);
            diaryFile.setFileBoardSort(FileBoardSort.diary);
            diaryFile.setFileSize(BigDecimal.valueOf(files.get(i).getSize() / 1024.0));
            diaryFile.setDiary(diary);

            files.get(i).transferTo(new File(filePath + "/" + saveName));
            savedFiles.add(fileRepository.save(diaryFile));
        }
        return savedFiles;
    }

    // 일기번호에 해당하는 파일 IsDeleted = true
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateFileDiaryIsDeleted(Diary diary) {

        List<FileDTO> fileDTOList = fileMapper.selectFilesByDiaryNo(diary.getDiaryNo());

        List<FileEntity> fileList = fileDTOList.stream()
                .map(fileDTO -> modelMapper.map(fileDTO, FileEntity.class))
                .collect(Collectors.toList());

        if (fileList.isEmpty()) {
            throw new CommonException(HttpStatusCode.NOT_FOUND_FILE);
        }

        for (FileEntity file : fileList) {
            file.setFileIsDeleted(true);
        }

        fileRepository.saveAll(fileList);
    }
}
