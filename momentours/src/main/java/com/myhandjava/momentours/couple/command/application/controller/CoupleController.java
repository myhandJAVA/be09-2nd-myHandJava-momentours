package com.myhandjava.momentours.couple.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.application.service.CoupleServiceImpl;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistNumberVO;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleUpdateVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/couple")
public class CoupleController {

    private final ModelMapper modelMapper;
    private final CoupleServiceImpl coupleService;

    @Autowired
    public CoupleController(ModelMapper modelMapper, CoupleServiceImpl coupleService) {
        this.modelMapper = modelMapper;
        this.coupleService = coupleService;
    }

    @PostMapping("")
    public ResponseEntity<String> registNewCouple(@RequestBody CoupleRegistNumberVO userNums) {
        String userNumber = userNums.getCoupleUserNo1() + "," + userNums.getCoupleUserNo2();

        HttpHeaders headers = new HttpHeaders();
        headers.add("userNumber", userNumber);

        return ResponseEntity.ok()
                .headers(headers)
                .body("회원 번호 2개 리턴 성공");
    }

    @PostMapping("/profile")
    public ResponseEntity<?> fillCoupleInfo(@RequestHeader HttpHeaders headers,
                                                          @RequestBody CoupleRegistVO coupleInfo) {
        List<String> userNumbers = headers.get("userNumber");

        if (userNumbers == null || userNumbers.isEmpty()) {
            return ResponseEntity.badRequest().body("헤더에 저장된 회원 번호가 없습니다.");
        }

        // 회원 번호 2개를 가져오기
        String[] userNos = userNumbers.get(0).split(",");
        if (userNos.length != 2) {
            return ResponseEntity.badRequest().body("잘못된 형식의 회원 번호.");
        }

        int userNo1 = Integer.parseInt(userNos[0]);
        int userNo2 = Integer.parseInt(userNos[1]);

        // 입력된 커플 정보를 DTO로 변환
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CoupleDTO coupleDTO = modelMapper.map(coupleInfo, CoupleDTO.class);

        // 커플 정보를 저장
        coupleService.inputCoupleInfo(userNo1, userNo2, coupleDTO);

        Map<String, Object> map = new HashMap<>();
        map.put("updatedCouple", coupleDTO);
        return ResponseEntity.ok(
                new ResponseMessage(
                        200, "커플 등록이 성공적으로 되었습니다!", map
                )
        );
    }

    @PatchMapping("")
    public ResponseEntity<ResponseMessage> updateCoupleInfo(@RequestBody CoupleUpdateVO updateInfo) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CoupleDTO updatedCouple = modelMapper.map(updateInfo, CoupleDTO.class);

        coupleService.updateCouple(updateInfo.getCoupleNo(), updatedCouple);

        Map<String, Object> map = new HashMap<>();
        map.put("updatedCouple", updatedCouple);
        return ResponseEntity.ok(
                new ResponseMessage(
                        200, "커플 정보가 수정되었습니다.", map
                ));
    }

}
