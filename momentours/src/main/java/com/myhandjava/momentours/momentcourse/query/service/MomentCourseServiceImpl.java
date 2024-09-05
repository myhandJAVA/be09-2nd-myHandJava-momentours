package com.myhandjava.momentours.momentcourse.query.service;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.query.repository.MomentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MomentCourseServiceImpl implements MomentCourseService {

    private final MomentCourseMapper momentCourseMapper;

    @Autowired
    public MomentCourseServiceImpl(MomentCourseMapper momentCourseMapper) {
        this.momentCourseMapper = momentCourseMapper;
    }

    // 전체 조회
    @Override
    public List<MomentCourseDTO> findAllMomentCourse(int momCourseCoupleNo) {
        List<MomentCourseDTO> result = momentCourseMapper.findAllMomentCourse(momCourseCoupleNo);

        return result;
    }

    // 상세 조회
    @Override
    public List<MomentCourseDTO> findMomentCourseByMomCourseNo(MomentCourseDTO momentCourseDTO) {
        List<MomentCourseDTO> result = momentCourseMapper.findMomentCourseByMomCourseNo(momentCourseDTO);

        return result;
    }

    // 검색 조회
    @Override
    public List<MomentCourseDTO> searchMomentCourse(Map<String, Object> searchMap) {
        List<MomentCourseDTO> result = momentCourseMapper.searchMomentCourse(searchMap);

        return result;
    }

}
