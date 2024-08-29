package com.myhandjava.momentours.diary.command.application.service;

import com.myhandjava.momentours.diary.command.application.dto.DiaryDTO;
import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.diary.command.domain.repository.DiaryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("diaryCommandServiceImpl")
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, ModelMapper modelMapper) {
        this.diaryRepository = diaryRepository;
        this.modelMapper = modelMapper;
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
        Diary diary = diaryRepository.findByDiaryNoAndDiaryUserNo(diaryNo, userNo);

        if (diary != null) {
            diary.setDiaryIsDeleted(1);
            diaryRepository.save(diary);
        } else {
            throw new EntityNotFoundException("일기가 없습니다.");
        }

    }
}
