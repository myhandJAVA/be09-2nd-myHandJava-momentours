package com.myhandjava.momentours.file.command.application.service;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.file.command.domain.aggregate.FileBoardSort;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import com.myhandjava.momentours.file.command.domain.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, ResourceLoader resourceLoader) {
        this.fileRepository = fileRepository;
        this.resourceLoader = resourceLoader;
    }

    public List<FileEntity> saveFile(List<MultipartFile> files, Diary diary) throws IOException {
        if(files.isEmpty()) {
            throw new IOException("업로드된 파일이 없습니다.");
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

            System.out.println("filePath = " + filePath);
            System.out.println("diary = " + diary);

            FileEntity diaryFile = new FileEntity();
            diaryFile.setFileOriginalName(originalFilename);
            diaryFile.setFileExtension(extension);
            diaryFile.setFileSaveName(saveName);
            diaryFile.setFileExtension(extension);
            diaryFile.setFileDirectory(filePath);
            diaryFile.setFileBoardSort(FileBoardSort.DIARY);
            diaryFile.setFileSize(BigDecimal.valueOf(files.get(i).getSize() / 1024.0));
            diaryFile.setDiary(diary);

            files.get(i).transferTo(new File(filePath + "/" + saveName));
            savedFiles.add(fileRepository.save(diaryFile));
        }
        return savedFiles;
    }
}
