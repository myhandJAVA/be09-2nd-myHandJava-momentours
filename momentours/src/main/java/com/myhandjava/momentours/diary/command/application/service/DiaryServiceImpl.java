package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.diary.command.domain.repository.DiaryRepository;
import com.myhandjava.momentours.file.command.application.service.FileService;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import com.myhandjava.momentours.file.command.domain.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("diaryCommandServiceImpl")
@Slf4j
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final FileRepository fileRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, 
                            ModelMapper modelMapper, 
                            FileService fileService, 
                            FileRepository fileRepository) {
        this.diaryRepository = diaryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public void registDiary(DiaryDTO newDiary) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Diary diary = modelMapper.map(newDiary, Diary.class);

        diaryRepository.save(diary);
    }

    @Override
    @Transactional
    public void removeDiary(int diaryNo, int userNo) {
        Diary diary = diaryRepository.findByDiaryNoAndDiaryUserNo(diaryNo, userNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 일기가 존재하지 않습니다."));;

        if (diary != null) {
            DiaryDTO diaryDTO = new DiaryDTO();
            diaryDTO.setDiaryIsDeleted(1);

            Diary diary1 = modelMapper.map(diaryDTO, Diary.class);
            diaryRepository.save(diary1);
        } else {
            throw new EntityNotFoundException("해당 일기가 존재하지 않습니다.");
        }

    }

    @Override
    @Transactional
    public void modifyDiary(DiaryDTO diaryDTO, int userNo, int diaryNo) throws IOException {
        Diary diary = diaryRepository.findByDiaryNoAndDiaryUserNo(diaryNo, userNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 일기가 존재하지 않습니다."));

        diary.setDiaryContent(diaryDTO.getDiaryContent());
        diary.setDiaryUpdateDate(diaryDTO.getDiaryUpdateDate());
        
        diaryRepository.save(diary);

        fileRepository.deleteByDiary(diary);

        if(diaryDTO.getFiles() != null && !diaryDTO.getFiles().isEmpty()) {
            fileService.saveFile(diaryDTO.getFiles(), diary);
        }
    }
}
