package com.myhandjava.momentours.momentcourse.query.repository;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MomentCourseMapper {

    // 추억 코스 전체 조회
    List<MomentCourseDTO> findAllMomentCourse(int momCourseCoupleNo);

    // 추억 코스 상세 조회
    List<MomentCourseDTO> findMomentCourseByMomCourseNo(MomentCourseDTO momentCourseDTO);
}
