package com.myhandjava.momentours.todocourse.command.application.service;

import com.myhandjava.momentours.todocourse.command.application.dto.TodoCourseDTO;

public interface TodoCourseService {

    // 예정 코스 등록
    void registTodoCourse(TodoCourseDTO newTodoCourse);
}
