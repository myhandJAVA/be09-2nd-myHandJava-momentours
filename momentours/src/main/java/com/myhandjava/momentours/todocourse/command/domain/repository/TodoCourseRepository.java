package com.myhandjava.momentours.todocourse.command.domain.repository;

import com.myhandjava.momentours.todocourse.command.domain.aggregate.TodoCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoCourseRepository extends JpaRepository<TodoCourse, Integer> {
}
