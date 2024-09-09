package com.myhandjava.momentours.moment.command.application.controller;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.application.service.MomentService;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    private MomentService momentService;

    @GetMapping("couple")
    public List<ResponseFindMomentByCoupleNoVO> findMomentByMomentCoupleNo(@RequestAttribute("claims") Claims claims) {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        return momentService.findMomentByMomentCoupleNo(coupleNo);
    }

    @GetMapping("/public/{momentPublic}")
    public List<ResponseFindMomentByMomentPublicVO> findMomentByMomentPublic(@PathVariable int momentPublic) {
        return momentService.findMomentByMomentPublic(momentPublic);
    }

    @PostMapping("/regist")
    public ResponseEntity<String> registMoment(@RequestBody MomentDTO newMoment) {
        momentService.registMoment(newMoment);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateByUser")
    public ResponseEntity<String> updateMoment(@RequestBody MomentDTO updatedMomentDTO,
                                               @RequestAttribute("claims") Claims claims) throws NotFoundException {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        updatedMomentDTO.setMomentCoupleNo(coupleNo);
        momentService.updateMomentByTitleAndCoupleNo(
                updatedMomentDTO.getMomentNo(),
                updatedMomentDTO.getMomentCoupleNo(),
                updatedMomentDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{momentNo}")
    public ResponseEntity<String> removeMoment(@PathVariable int momentNo,
                                               @RequestAttribute("claims") Claims claims) throws NotFoundException {
        int coupleNo = Integer.parseInt(claims.get("coupleNo", String.class));
        momentService.removeMoment(momentNo, coupleNo);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{momentNo}/delete")
    public ResponseEntity<String> softRemoveMoment(@PathVariable int momentNo) throws NotFoundException {
        momentService.softRemoveMoment(momentNo);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{momentNo}")
    public ResponseEntity<MomentDTO> getMoment(@PathVariable int momentNo,
                                               @CookieValue(value = "viewedMoments", defaultValue = "")String viewedMoments,
                                               HttpServletResponse response) throws NotFoundException {

        // 조회한 게시물 목록을 쿠키에서 가져오기, 중복이면 안되기에 SET 사용
        Set<Integer> viewedMomentIds = Arrays.stream(viewedMoments.split(","))
                .filter(id -> !id.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        // 해당 게시물이 쿠키에 없으면 증가
        if (!viewedMomentIds.contains(momentNo)) {
            momentService.incrementViewCount(momentNo);
            viewedMomentIds.add(momentNo);

            // 업데이트 된 쿠키 다시 설정
            Cookie cookie = new Cookie("viewedMoments", String.join(",", viewedMomentIds.stream()
                    .map(String::valueOf).toArray(String[]::new)));
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
        }

        // 해당 게시물의 정보를 반환
        MomentDTO momentDTO = momentService.getMomentById(momentNo);
        return ResponseEntity.ok().body(momentDTO);
    }

    @PostMapping("/{momentNo}/like")
    public ResponseEntity<String> likeMoment(@PathVariable int momentNo,
                                             @CookieValue(value = "likedMoments", defaultValue = "")String likedMoments,
                                             HttpServletResponse response) throws NotFoundException {

        Set<Integer> likedMomentsIds = Arrays.stream(likedMoments.split("-"))
                .filter(id -> !id.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        if (!likedMomentsIds.contains(momentNo)){
            // 좋아요 추가
            momentService.incrementLike(momentNo);
            likedMomentsIds.add(momentNo);
        } else {
            // 좋아요 취소
            momentService.decrementLike(momentNo);
            likedMomentsIds.remove(momentNo);
        }

        String updatedLikedMoments = likedMomentsIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));

        // 조건문 통과한 것을 쿠키에 담기
        Cookie cookie = new Cookie("likedMoments", updatedLikedMoments);
        cookie.setPath("/");
        // 쿠키 1년
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);

        // 바디는 클라이언트에게 보이지 않으니 db 직접 확인
        return ResponseEntity.ok().build();
    }

}
