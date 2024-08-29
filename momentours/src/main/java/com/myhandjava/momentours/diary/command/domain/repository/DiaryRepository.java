package com.myhandjava.momentours.diary.command.domain.repository;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
