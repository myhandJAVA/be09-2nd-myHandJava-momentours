package com.myhandjava.momentours.momentcourse.command.application.service;


import com.myhandjava.momentours.momentcourse.command.application.dto.MomcourselocationDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.Momcourselocation;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourse;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomcourselocationRepository;
import com.myhandjava.momentours.momentcourse.command.domain.repository.MomentCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("CommandMomentCourseService")
@Slf4j
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

    //추억 코스 삭제
    @Override
    @Transactional
    public void removeMomentCourse(int momCourseNo, int momCourseCoupleNo) {
        MomentCourse momentCourse = momentCourseRepository.findByMomCourseNoAndMomCourseCoupleNo(momCourseNo, momCourseCoupleNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 추억코스가 존재하지 않습니다."));

        momentCourse.setMomCourseIsDeleted(true);
        momentCourseRepository.save(momentCourse);
    }

    // 추억 코스 수정
    @Override
    @Transactional
    public void modifyMomentCourse(int momCourseNo, MomentCourseDTO momentCourseDTO) {

        momentCourseDTO.setMomCourseUpdateDate(LocalDateTime.now());
        MomentCourse momentCourse = momentCourseRepository.findByMomCourseNoAndMomCourseCoupleNo(momCourseNo, momentCourseDTO.getMomCourseNo())
                .orElseThrow(() -> new EntityNotFoundException("해당 추억 코스가 존재하지 않습니다."));

        momentCourse.setMomCourseTitle(momentCourseDTO.getMomCourseTitle());
        momentCourse.setMomCourseMemo(momentCourseDTO.getMomCourseMemo());
        momentCourse.setMomCoursePublic(momentCourseDTO.isMomCoursePublic());
        if(momentCourseDTO.getMomCourseSort().equals(MomentCourseSort.fewDay))
            momentCourse.setMomCourseSort(MomentCourseSort.fewDay);
        else momentCourse.setMomCourseSort(MomentCourseSort.oneDay);

        momentCourseRepository.save(momentCourse);
        momcourselocationRepository.deleteByMomCourseNo(momCourseNo);

        for (Integer momentNo : momentCourseDTO.getMomentNos()) {
            MomcourselocationDTO momcourselocationDTO = new MomcourselocationDTO();
            momcourselocationDTO.setMomentNo(momentNo);
            momcourselocationDTO.setMomCourseNo(momCourseNo);

            Momcourselocation momcourselocation = modelMapper.map(momcourselocationDTO, Momcourselocation.class);

            momcourselocationRepository.save(momcourselocation);
        }
    }

    // 추억코스 좋아요
    @Override
    @Transactional
    public void incrementLike(int momCourseNo) {
        MomentCourse momentCourse = momentCourseRepository.findById(momCourseNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 추억 코스가 존재하지 않습니다."));

        momentCourse.setMomCourseLike(momentCourse.getMomCourseLike() + 1);
        momentCourseRepository.save(momentCourse);
    }

    // 추억코스 좋아요 취소
    @Override
    @Transactional
    public void decrementLike(int momCourseNo) {
        MomentCourse momentCourse = momentCourseRepository.findById(momCourseNo)
                .orElseThrow(() -> new EntityNotFoundException("해당 추억 코스가 존재하지 않습니다."));

        if(momentCourse.getMomCourseLike() > 0) {
            momentCourse.setMomCourseLike(momentCourse.getMomCourseLike() - 1);
            momentCourseRepository.save(momentCourse);
        }
    }
}
