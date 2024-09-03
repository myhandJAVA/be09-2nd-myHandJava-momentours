package com.myhandjava.momentours.momentcourse.command.application.service;


import com.myhandjava.momentours.momentcourse.command.application.dto.MomcourselocationDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Momcourselocation;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourse;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomcourselocationRepository;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomentCourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("CommandMomentCourseService")
public class MomentCourseServiceImpl implements MomentCourseService {

    private final MomentCourseRepository momentCourseRepository;
    private final MomcourselocationRepository momcourselocationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MomentCourseServiceImpl(MomentCourseRepository momentCourseRepository, MomcourselocationRepository momcourselocationRepository, ModelMapper modelMapper) {
        this.momentCourseRepository = momentCourseRepository;
        this.momcourselocationRepository = momcourselocationRepository;
        this.modelMapper = modelMapper;
    }

    // 추억 코스 등록
    @Override
    @Transactional
    public void registMomentCourse(MomentCourseDTO momentCourseDTO) {
        momentCourseDTO.setMomCourseCreateDate(LocalDateTime.now());
        if(momentCourseDTO.getMomCourseSort().equals(MomentCourseSort.fewDay))
            momentCourseDTO.setMomCourseSort(MomentCourseSort.fewDay);
        else momentCourseDTO.setMomCourseSort(MomentCourseSort.oneDay);

        MomentCourse momentCourse = modelMapper.map(momentCourseDTO, MomentCourse.class);

        momentCourseRepository.save(momentCourse);

        for (Integer momentNo : momentCourseDTO.getMomentNos()) {
            MomcourselocationDTO momcourselocationDTO = new MomcourselocationDTO();
            momcourselocationDTO.setMomentNo(momentNo);
            momcourselocationDTO.setMomCourseNo(momentCourse.getMomCourseNo());

            Momcourselocation momcourselocation = modelMapper.map(momcourselocationDTO, Momcourselocation.class);
            momcourselocationRepository.save(momcourselocation);
        }

    }
}
