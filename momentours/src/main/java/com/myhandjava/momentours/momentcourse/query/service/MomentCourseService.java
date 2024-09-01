package com.myhandjava.momentours.momentcourse.query.service;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;

import java.util.List;

public interface MomentCourseService {
    List<MomentCourseDTO> findAllMomentCourse(int momCourseCoupleNo);

    List<MomentCourseDTO> findMomentCourseByMomCourseNo(MomentCourseDTO momentCourseDTO);
}
