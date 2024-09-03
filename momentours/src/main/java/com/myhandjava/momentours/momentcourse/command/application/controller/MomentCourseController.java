package com.myhandjava.momentours.momentcourse.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.application.service.MomentCourseService;
import com.myhandjava.momentours.momentcourse.command.domain.vo.RequestRegistMomCourseVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("CommandMomentCourseController")
@RequestMapping("/momentcourse")
@Slf4j
public class MomentCourseController {

    private final MomentCourseService momentCourseService;
    private final ModelMapper modelMapper;

    @Autowired
    public MomentCourseController(MomentCourseService momentCourseService, ModelMapper modelMapper) {
        this.momentCourseService = momentCourseService;
        this.modelMapper = modelMapper;
    }


    // 추억 코스 등록
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registMomentCourse(@RequestBody RequestRegistMomCourseVO newMomentCourse) {
        log.info("newMomentCourse {}", newMomentCourse);
        MomentCourseDTO momentCourseDTO = modelMapper.map(newMomentCourse, MomentCourseDTO.class);
        momentCourseService.registMomentCourse(momentCourseDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("newMomentCourse", newMomentCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "추억코스 등록 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseMessage);
    }

    // 추억 코스 삭제
    @DeleteMapping("/{momCourseNo}")
    public ResponseEntity<?> removeMomentCourse(@PathVariable int momCourseNo,
                                                              @RequestParam int momCourseCoupleNo) {

        momentCourseService.removeMomentCourse(momCourseNo, momCourseCoupleNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
