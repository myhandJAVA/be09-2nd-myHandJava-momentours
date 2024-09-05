package com.myhandjava.momentours.todocourse.command.domain.repository;

import com.myhandjava.momentours.todocourse.command.domain.aggregate.Todocourselocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCourselocationRepository extends JpaRepository<Todocourselocation, Integer> {
    void deleteByToDoCourseNo(int todoCourseNo);
}
