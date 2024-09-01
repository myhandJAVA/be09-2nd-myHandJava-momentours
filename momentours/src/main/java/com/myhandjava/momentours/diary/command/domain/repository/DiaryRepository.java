package com.myhandjava.momentours.diary.command.domain.repository;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    Optional<Diary> findByDiaryNoAndDiaryUserNo(int diaryNo, int userNo);
}
