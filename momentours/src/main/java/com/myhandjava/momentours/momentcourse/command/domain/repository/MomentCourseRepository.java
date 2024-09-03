package com.myhandjava.momentours.momentcourse.command.domain.repository;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MomentCourseRepository extends JpaRepository<MomentCourse, Integer> {
    Optional<MomentCourse> findByMomCourseNoAndMomCourseCoupleNo(int momCourseNo, int momCourseCoupleNo);
}
