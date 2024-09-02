package com.myhandjava.momentours.todocourse.query.service;

import com.myhandjava.momentours.todocourse.query.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.query.repository.TodoCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoCourseServiceImpl implements TodoCourseService {

    private final TodoCourseMapper todoCourseMapper;

    @Autowired
    public TodoCourseServiceImpl(TodoCourseMapper todoCourseMapper) {
        this.todoCourseMapper = todoCourseMapper;
    }

    // 전체 조회
    @Override
    public List<TodoCourseDTO> findAllTodoCourse(int todoCourseCoupleNo) {
        List<TodoCourseDTO> result = todoCourseMapper.findAllTodoCourse(todoCourseCoupleNo);

        return result;
    }

    // 상세 조회
    @Override
    public List<TodoCourseDTO> findTodoCourseByTodoCourseNo(TodoCourseDTO todoCourseDTO) {
        List<TodoCourseDTO> result = todoCourseMapper.findTodoCourseByTodoCourseNo(todoCourseDTO);

        return result;
    }
}
