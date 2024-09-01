package com.myhandjava.momentours.moment.command.domain.repository;

import com.myhandjava.momentours.moment.command.domain.aggregate.Moment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MomentRepository extends JpaRepository<Moment, Long> {
    List<Moment> findMomentByMomentCoupleNo(int momentCoupleNo, Sort sort);

    List<Moment> findMomentByMomentPublic(int momentPublic, Sort sort);
}
