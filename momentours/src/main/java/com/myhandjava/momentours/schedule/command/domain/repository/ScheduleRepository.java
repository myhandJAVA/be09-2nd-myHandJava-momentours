package com.myhandjava.momentours.schedule.command.domain.repository;


import com.myhandjava.momentours.schedule.command.domain.aggregate.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {

    Optional<Schedule> findByScheduleTitle(String title);
}
