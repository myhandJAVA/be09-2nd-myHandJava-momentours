package com.myhandjava.momentours.couple.command.domain.repository;

import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Integer> {
    Optional<Couple> findByCoupleNo(int coupleNo);
}
