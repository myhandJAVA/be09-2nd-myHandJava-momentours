package com.myhandjava.momentours.todocourse.command.application.service;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.todocourse.command.application.dto.TodoCourseDTO;
import com.myhandjava.momentours.todocourse.command.application.dto.TodocourselocationDTO;
import com.myhandjava.momentours.todocourse.command.domain.aggregate.TodoCourse;
import com.myhandjava.momentours.todocourse.command.domain.aggregate.Todocourselocation;
import com.myhandjava.momentours.todocourse.command.domain.repository.TodoCourseRepository;
import com.myhandjava.momentours.todocourse.command.domain.repository.TodoCourselocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("todoCourseCommandServiceImpl")
@Slf4j
public class TodoCourseServiceImpl implements TodoCourseService {

    private final TodoCourseRepository todoCourseRepository;
    private final TodoCourselocationRepository todoCourselocationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoCourseServiceImpl(TodoCourseRepository todoCourseRepository,
                                 ModelMapper modelMapper,
                                 TodoCourselocationRepository todoCourselocationRepository) {
        this.todoCourseRepository = todoCourseRepository;
        this.modelMapper = modelMapper;
        this.todoCourselocationRepository = todoCourselocationRepository;
    }

    // 예정 코스 등록
    @Override
    @Transactional
    public void registTodoCourse(TodoCourseDTO todoCourseDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        todoCourseDTO.setToDoCourseCreateDate(LocalDateTime.now());
        todoCourseDTO.setToDoCourseLike(0);
        todoCourseDTO.setToDoCourseIsDeleted(false);
        if(todoCourseDTO.getToDoCourseSort().equals(MomentCourseSort.fewDay))
            todoCourseDTO.setToDoCourseSort(MomentCourseSort.fewDay);
        else todoCourseDTO.setToDoCourseSort(MomentCourseSort.oneDay);

        TodoCourse todoCourse = new TodoCourse();
        todoCourse.setToDoCourseCoupleNo(todoCourseDTO.getToDoCourseCoupleNo());
        todoCourse.setToDoCourseTitle(todoCourseDTO.getToDoCourseTitle());
        todoCourse.setToDoCourseSort(todoCourseDTO.getToDoCourseSort());
        todoCourse.setToDoCourseStartDate(todoCourseDTO.getToDoCourseStartDate());
        todoCourse.setToDoCourseEndDate(todoCourseDTO.getToDoCourseEndDate());
        todoCourse.setToDoCourseMemo(todoCourseDTO.getToDoCourseMemo());
        todoCourse.setToDoCourseCreateDate(todoCourseDTO.getToDoCourseCreateDate());

        todoCourseRepository.save(todoCourse);

        for (Integer todoNo : todoCourseDTO.getTodoNos()) {
            Todocourselocation todocourselocation = new Todocourselocation();
            todocourselocation.setToDoCourseNo(todoCourse.getToDoCourseNo());
            todocourselocation.setLocationNo(todoNo);

            todoCourselocationRepository.save(todocourselocation);
        }
    }

    // 예정 코스 수정
    @Override
    @Transactional
    public void modifyTodoCourse(int todoCourseNo, TodoCourseDTO todoCourseDTO) {

        todoCourseDTO.setToDoCourseUpdateDate(LocalDateTime.now());
        TodoCourse todoCourse = todoCourseRepository.findByToDoCourseNoAndToDoCourseCoupleNo(todoCourseNo, todoCourseDTO.getToDoCourseCoupleNo())
                .orElseThrow(() -> new EntityNotFoundException("해당 예정 코스가 존재하지 않습니다."));

        todoCourse.setToDoCourseTitle(todoCourseDTO.getToDoCourseTitle());
        todoCourse.setToDoCourseMemo(todoCourseDTO.getToDoCourseMemo());
        todoCourse.setToDoCourseStartDate(todoCourseDTO.getToDoCourseStartDate());
        todoCourse.setToDoCourseEndDate(todoCourseDTO.getToDoCourseEndDate());
        if(todoCourseDTO.getToDoCourseSort().equals(MomentCourseSort.fewDay))
            todoCourse.setToDoCourseSort(MomentCourseSort.fewDay);
        else todoCourse.setToDoCourseSort(MomentCourseSort.oneDay);

        todoCourseRepository.save(todoCourse);
        todoCourselocationRepository.deleteByToDoCourseNo(todoCourseNo);

        for (Integer todoNo : todoCourseDTO.getTodoNos()) {
            Todocourselocation todocourselocationDTO = new Todocourselocation();
            todocourselocationDTO.setLocationNo(todoNo);
            todocourselocationDTO.setToDoCourseNo(todoCourseNo);

            Todocourselocation todocourselocation = modelMapper.map(todocourselocationDTO, Todocourselocation.class);

            todoCourselocationRepository.save(todocourselocation);
        }
    }

    // 예정 코스 삭제
    @Override
    @Transactional
    public void removeTodoCourse(int todoCourseNo, int todoCourseCoupleNo) {
        TodoCourse todoCourse = todoCourseRepository.findByToDoCourseNoAndToDoCourseCoupleNo(todoCourseNo, todoCourseCoupleNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 예정코스가 존재하지 않습니다."));

        todoCourse.setToDoCourseIsDeleted(true);
        todoCourseRepository.save(todoCourse);
    }
}
