package com.myhandjava.momentours.momentcourse.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.momentcourse.command.application.dto.FavoriteDTO;
import com.myhandjava.momentours.momentcourse.command.application.dto.MomentCourseDTO;
import com.myhandjava.momentours.momentcourse.command.application.service.MomentCourseService;
import com.myhandjava.momentours.momentcourse.command.domain.vo.RequestModifyMomCourseVO;
import com.myhandjava.momentours.momentcourse.command.domain.vo.RequestRegistMomCourseVO;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Post momentcourse", description = "추억코스를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "추억코스 등록 성공!",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registMomentCourse(@RequestBody RequestRegistMomCourseVO newMomentCourse) {

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
    @Operation(summary = "Delete momentcourse", description = "추억코스를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "추억코스 삭제 성공!"),
            @ApiResponse(responseCode = "40405", description = "해당 추억코스가 존재하지 않습니다.")
    })
    @DeleteMapping("/{momCourseNo}")
    public ResponseEntity<?> removeMomentCourse(@Parameter(description = "삭제 할 추억코스 번호", required = true, example = "1") @PathVariable int momCourseNo,
                                                @RequestBody int coupleNo) {

        momentCourseService.removeMomentCourse(momCourseNo, coupleNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    // 추억 코스 수정
    @Operation(summary = "Put momentcourse", description = "추억코스를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 수정 성공!"),
            @ApiResponse(responseCode = "40405", description = "해당 추억코스가 존재하지 않습니다.")
    })
    @PutMapping("/{momCourseNo}")
    public ResponseEntity<ResponseMessage> modifyMomentCourse(@Parameter(description = "수정 할 추억코스 번호", required = true, example = "1") @PathVariable int momCourseNo,
                                                              @RequestBody RequestModifyMomCourseVO modifyMomentCourse){
        MomentCourseDTO momentCourseDTO = modelMapper.map(modifyMomentCourse, MomentCourseDTO.class);
        momentCourseService.modifyMomentCourse(momCourseNo, momentCourseDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyMomentCourse", modifyMomentCourse);

        ResponseMessage responseMessage = new ResponseMessage(201, "추억 코스 수정 성공!", responseMap);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMessage);
    }

    // 추억 코스 좋아요
    @Operation(summary = "Post momentcourse", description = "추억코스를 좋아요합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 좋아요 성공!"),
            @ApiResponse(responseCode = "40405", description = "해당 추억코스가 존재하지 않습니다.")
    })
    @PostMapping("/like/{momCourseNo}")
    public ResponseEntity<ResponseMessage> likeMomentCourse(@Parameter(description = "좋아요 할 추억코스 번호", required = true, example = "1") @PathVariable int momCourseNo,
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

    // 추억코스 즐겨찾기
    @Operation(summary = "Post favorite momentcourse", description = "추억코스를 즐겨찾기합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "추억코스 즐겨찾기 성공!"),
            @ApiResponse(responseCode = "40405", description = "해당 추억코스가 존재하지 않습니다.")
    })
    @PostMapping("/favorite/{momCourseNo}")
    public ResponseEntity<String> FavoriteMomentCourse(@Parameter(description = "즐겨찾기 할 추억코스 번호", required = true, example = "1") @PathVariable int momCourseNo,
                                                       @RequestBody FavoriteDTO favoriteDTO) {
        favoriteDTO.setFavoMomCourseNo(momCourseNo);
        boolean isFavorite = momentCourseService.isFavorite(favoriteDTO);
        if(isFavorite)
            return ResponseEntity.status(HttpStatus.OK).body("추억코스가 즐겨찾기에 추가되었습니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body("추억코스가 즐겨찾기에서 취소되었습니다.");
    }
}
