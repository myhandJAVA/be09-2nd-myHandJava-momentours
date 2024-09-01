package com.myhandjava.momentours.file.command.domain.repository;

import com.myhandjava.momentours.diary.command.domain.aggregate.Diary;
import com.myhandjava.momentours.file.command.domain.aggregate.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    void deleteByDiary(Diary diary);
}
