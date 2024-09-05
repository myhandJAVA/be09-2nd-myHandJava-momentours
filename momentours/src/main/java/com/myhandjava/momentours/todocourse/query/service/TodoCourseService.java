package com.myhandjava.momentours.todocourse.query.service;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;

import java.util.List;
import java.util.Map;

public interface TodoCourseService {

    // 전체 조회
    List<TodoCourseDTO> findAllTodoCourse(int todoCourseCoupleNo);

    // 상세 조회
    List<TodoCourseDTO> findTodoCourseByTodoCourseNo(TodoCourseDTO todoCourseDTO);

    // 검색
    List<TodoCourseDTO> searchToDoCourse(Map<String, Object> searchMap);
}
