package com.myhandjava.momentours.diary.command.domain.repository;

import com.myhandjava.momentours.diary.command.domain.aggregate.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryRepository extends JpaRepository<Temporary, Integer> {
}
