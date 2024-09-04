package com.myhandjava.momentours.momentcourse.query.controller;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.query.service.MomentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/momentcourse")
public class MomentCourseController {

    private MomentCourseService momentCourseService;

    @Autowired
    public MomentCourseController(MomentCourseService momentCourseService) {
        this.momentCourseService = momentCourseService;
    }

    // 커플 번호를 통해 해당 커플의 전체 추억 코스 조회
    @GetMapping("/{momCourseCoupleNo}")     // 이렇게 해도 되는건가?
    public List<MomentCourseDTO> findAllMomentCourse(@PathVariable int momCourseCoupleNo) {
        List<MomentCourseDTO> momentCourseList = momentCourseService.findAllMomentCourse(momCourseCoupleNo);

        return momentCourseList;
    }

    // 추억 코스 번호로 상세 조회
    @GetMapping("/{momCourseCoupleNo}/{momCourseNo}")
    public List<MomentCourseDTO> findMomentCourseByMomCourseNo(@PathVariable int momCourseCoupleNo,
                                                               @PathVariable int momCourseNo) {

        MomentCourseDTO momentCourseDTO = new MomentCourseDTO();

        momentCourseDTO.setMomCourseCoupleNo(momCourseCoupleNo);
        momentCourseDTO.setMomCourseNo(momCourseNo);

        List<MomentCourseDTO> momentCourseList = momentCourseService.findMomentCourseByMomCourseNo(momentCourseDTO);

        return momentCourseList;
    }

    @GetMapping("/search")
    public List<MomentCourseDTO> searchMomentCourse(@RequestParam(required = false) String searchCondition,
                                                    @RequestParam(required = false) String keyword) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("keyword", keyword);

        List<MomentCourseDTO> searchList = momentCourseService.searchMomentCourse(searchMap);

        return searchList;
    }
}
