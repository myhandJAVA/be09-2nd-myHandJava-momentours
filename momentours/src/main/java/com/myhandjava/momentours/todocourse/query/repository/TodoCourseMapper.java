package com.myhandjava.momentours.todocourse.query.repository;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoCourseMapper {

    // 예정 코스 전체 조회
    List<TodoCourseDTO> findAllTodoCourse(int todoCourseCoupleNo);

    // 예정 코스 상세 조회
    List<TodoCourseDTO> findTodoCourseByTodoCourseNo(TodoCourseDTO todoCourseDTO);
}
