package com.myhandjava.momentours.todocourse.command.domain.repository;

import com.myhandjava.momentours.todocourse.command.domain.aggregate.TodoCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoCourseRepository extends JpaRepository<TodoCourse, Integer> {
    Optional<TodoCourse> findByToDoCourseNoAndToDoCourseCoupleNo(int todoCourseNo, int toDoCourseCoupleNo);
}
