package com.myhandjava.momentours.momentcourse.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.momentcourse.command.application.dto.FavoriteDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.application.service.MomentCourseService;
import com.myhandjava.momentours.momentcourse.command.domain.vo.RequestModifyMomCourseVO;
import com.myhandjava.momentours.momentcourse.command.domain.vo.RequestRegistMomCourseVO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<ResponseMessage> registMomentCourse(@RequestBody RequestRegistMomCourseVO newMomentCourse,
                                                              @RequestAttribute("claims") Claims claims) {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));

        MomentCourseDTO momentCourseDTO = modelMapper.map(newMomentCourse, MomentCourseDTO.class);

        momentCourseDTO.setMomCourseCoupleNo(coupleNo);

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
                                                @RequestAttribute("claims") Claims claims) {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));

        momentCourseService.removeMomentCourse(momCourseNo, coupleNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 추억 코스 수정
    @PutMapping("/{momCourseNo}")
    public ResponseEntity<ResponseMessage> modifyMomentCourse(@PathVariable int momCourseNo,
                                                              @RequestBody RequestModifyMomCourseVO modifyMomentCourse,
                                                              @RequestAttribute("claims") Claims claims){
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        MomentCourseDTO momentCourseDTO = modelMapper.map(modifyMomentCourse, MomentCourseDTO.class);
        momentCourseDTO.setMomCourseCoupleNo(coupleNo);
        momentCourseService.modifyMomentCourse(momCourseNo, momentCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyMomentCourse", modifyMomentCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "추억 코스 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    // 추억 코스 좋아요
    @PostMapping("/like/{momCourseNo}")
    public ResponseEntity<ResponseMessage> likeMomentCourse(@PathVariable int momCourseNo,
                                                            @CookieValue(value = "momCourseLike", defaultValue = "") String momCourseLike,
                                                            HttpServletResponse reponse) {

        Set<Integer> momCourseLikeIds = Arrays.stream(momCourseLike.split("-"))
                .filter(id ->!id.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        if (!momCourseLikeIds.contains(momCourseNo)) {
            // 좋아요 추가
            momentCourseService.incrementLike(momCourseNo);
            momCourseLikeIds.add(momCourseNo);
        } else {
            // 좋아요 취소
            momentCourseService.decrementLike(momCourseNo);
            momCourseLikeIds.remove(momCourseNo);
        }

        String modifydMomCourseLike = momCourseLikeIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));

        Cookie cookie = new Cookie("momCourseLike", modifydMomCourseLike);
        cookie.setPath("/");

        cookie.setMaxAge(60 * 60 * 24 * 365);
        reponse.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/favorite/{momCourseNo}")
    public ResponseEntity<String> FavoriteMomentCourse(@RequestBody FavoriteDTO favoriteDTO,
                                                       @RequestAttribute("claims") Claims claims) {
        int userNo = Integer.parseInt(claims.get("userNo", String.class));
        favoriteDTO.setFavoUserNo(userNo);
        boolean isFavorite = momentCourseService.isFavorite(favoriteDTO);
        if(isFavorite)
            return ResponseEntity.status(HttpStatus.OK).body("추억코스가 즐겨찾기에 추가되었습니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body("추억코스가 즐겨찾기에서 취소되었습니다.");
    }
}
