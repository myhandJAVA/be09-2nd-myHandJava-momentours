package com.myhandjava.momentours.todocourse.command.application.service;

import com.myhandjava.momentours.todocourse.command.application.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.command.domain.aggregate.TodoCourse;
import com.myhandjava.momentours.todocourse.command.domain.repository.TodoCourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("todoCourseCommandServiceImpl")
@Slf4j
public class TodoCourseServiceImpl implements TodoCourseService {

    private final TodoCourseRepository todoCourseRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoCourseServiceImpl(TodoCourseRepository todoCourseRepository, ModelMapper modelMapper) {
        this.todoCourseRepository = todoCourseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registTodoCourse(TodoCourseDTO newTodoCourse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        newTodoCourse.setToDoCourseCreateDate(LocalDateTime.now());
        TodoCourse todoCourse = modelMapper.map(newTodoCourse, TodoCourse.class);

        todoCourseRepository.save(todoCourse);
    }
}
