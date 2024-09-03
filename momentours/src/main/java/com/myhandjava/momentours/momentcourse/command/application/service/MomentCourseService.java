package com.myhandjava.momentours.momentcourse.command.application.service;

import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;

public interface MomentCourseService {
    void registMomentCourse(MomentCourseDTO momentCourseDTO);

    void removeMomentCourse(int momCourseNo, int momCourseCoupleNo);

    void modifyMomentCourse(int momCourseNo, MomentCourseDTO momentCourseDTO);

    void incrementLike(int momCourseNo);

    void decrementLike(int momCourseNo);
}
