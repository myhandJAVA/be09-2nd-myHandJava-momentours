package com.myhandjava.momentours.couple.command.domain.repository;

import com.myhandjava.momentours.couple.command.domain.aggregate.Couple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoupleRepository extends JpaRepository<Couple, Integer> {
    Couple findByCoupleNo(int coupleNo);
    @Query("SELECT COALESCE(MAX(c.coupleNo), 0) FROM Couple c")
    Integer findMaxCoupleNo();
}
