package com.myhandjava.momentours.diary.command.domain.repository;

import com.myhandjava.momentours.diary.command.domain.aggregate.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemporaryRepository extends JpaRepository<Temporary, Integer> {
    Optional<List<Temporary>> findAllByDiaryNo(int diaryNo);
}
