package com.myhandjava.momentours.momentcourse.query.controller;

import com.myhandjava.momentours.momentcourse.query.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.query.service.MomentCourseService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all momentcourse", description = "추억코스를 전체 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 조회 성공!"),
            @ApiResponse(responseCode = "40405", description = "추억코스가 존재하지 않습니다.")
    })
    @GetMapping("")
    public List<MomentCourseDTO> findAllMomentCourse(@Parameter(description = "커플번호", required = true, example = "1") @RequestParam int coupleNo) {
        List<MomentCourseDTO> momentCourseList = momentCourseService.findAllMomentCourse(coupleNo);

        return momentCourseList;
    }

    // 추억 코스 번호로 상세 조회
    @Operation(summary = "Get detail momentcourse", description = "추억코스를 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 상세조회 성공!"),
            @ApiResponse(responseCode = "40405", description = "해당 추억코스가 존재하지 않습니다.")
    })
    @GetMapping("/{momCourseNo}")
    public List<MomentCourseDTO> findMomentCourseByMomCourseNo(@Parameter(description = "커플번호", required = true, example = "1") @RequestParam int coupleNo,
                                                               @Parameter(description = "추억코스 번호", required = true, example = "1") @PathVariable int momCourseNo) {

        MomentCourseDTO momentCourseDTO = new MomentCourseDTO();
        momentCourseDTO.setMomCourseCoupleNo(coupleNo);
        momentCourseDTO.setMomCourseNo(momCourseNo);

        List<MomentCourseDTO> momentCourseList = momentCourseService.findMomentCourseByMomCourseNo(momentCourseDTO);

        return momentCourseList;
    }

    // 검색조회
    @Operation(summary = "Get search momentcourse", description = "추억코스를 검색 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 검색조회 성공!"),
            @ApiResponse(responseCode = "40405", description = "추억코스가 존재하지 않습니다.")
    })
    @GetMapping("/search")
    public List<MomentCourseDTO> searchMomentCourse(@Parameter(description = "검색조건", required = true, example = "courseName") @RequestParam(required = false) String searchCondition,
                                                    @Parameter(description = "키워드", required = true, example = "여행") @RequestParam(required = false) String keyword) {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("keyword", keyword);

        List<MomentCourseDTO> searchList = momentCourseService.searchMomentCourse(searchMap);

        return searchList;
    }
}
