package com.myhandjava.momentours.moment.command.domain.repository;

import com.myhandjava.momentours.moment.command.domain.aggregate.Moment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MomentRepository extends JpaRepository<Moment, Long> {
    List<Moment> findMomentByMomentCoupleNo(int momentCoupleNo, Sort sort);

    List<Moment> findMomentByMomentPublic(int momentPublic, Sort sort);

    Optional<Moment> findByMomentNoAndMomentCoupleNo(int momentNo, int momentCoupleNo);
}
