package com.myhandjava.momentours.couple.command.domain.repository;

import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoupleRepository extends JpaRepository<Couple, Integer> {
    Couple findByCoupleNo(int coupleNo);
}
