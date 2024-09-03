package com.myhandjava.momentours.momentcourse.command.domain.repository;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Momcourselocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MomcourselocationRepository extends JpaRepository<Momcourselocation, Integer> {
}
