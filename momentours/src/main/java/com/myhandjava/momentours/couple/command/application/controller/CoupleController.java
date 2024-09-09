package com.myhandjava.momentours.couple.command.application.controller;

import com.myhandjava.momentours.client.UserClient;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.application.dto.CoupleRegisterDTO;
import com.myhandjava.momentours.couple.command.application.service.CoupleService;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleUpdateVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/couple")
@Slf4j
public class CoupleController {

    private final ModelMapper modelMapper;
    private final CoupleService coupleService;
    private final UserClient userClient;

    @Autowired
    public CoupleController(ModelMapper modelMapper, CoupleService coupleService, UserClient userClient) {
        this.modelMapper = modelMapper;
        this.coupleService = coupleService;
        this.userClient = userClient;
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage> registNewCouple(@RequestBody CoupleRegisterDTO coupleRegisterDTO) {
        int userNo1 = coupleRegisterDTO.getUserNo1();
        int userNo2 = coupleRegisterDTO.getUserNo2();
        int coupleNo = coupleService.registCouple(userNo1, userNo2);
        Map<String, Object> result = new HashMap<>();
        result.put("coupleNo", coupleNo);
        return ResponseEntity.ok(new ResponseMessage(200, "커플 객체 생성 성공", result));
    }

    @PostMapping("/profile")
    public ResponseEntity<ResponseMessage> fillCoupleInfo(@RequestAttribute("claims") Claims claims,
                                            @RequestBody CoupleRegistVO coupleInfo) {
        int coupleNo = (Integer) claims.get("coupleNo");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CoupleDTO coupleDTO = modelMapper.map(coupleInfo, CoupleDTO.class);
        coupleService.inputCoupleInfo(coupleNo, coupleDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("updatedCouple", coupleDTO);
        return ResponseEntity.ok(new ResponseMessage(200, "커플 정보 입력 성공", result));
    }


    @PutMapping("/profile")
    public ResponseEntity<ResponseMessage> updateCoupleInfo(@RequestBody CoupleUpdateVO updateInfo,
                                                            @RequestAttribute("claims") Claims claims) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        int coupleNo = (Integer)claims.get("coupleNo");
        CoupleDTO updatedCouple = modelMapper.map(updateInfo, CoupleDTO.class);
        coupleService.updateCouple(coupleNo, updatedCouple);
        Map<String, Object> map = new HashMap<>();
        map.put("updatedCouple", updatedCouple);
        return ResponseEntity.ok(new ResponseMessage(200, "커플 정보가 수정되었습니다.", map));
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> deleteCoupleInfo(@RequestAttribute("claims") Claims claims) {
        int coupleNo = (Integer)claims.get("coupleNo");
        coupleService.deleteCouple(coupleNo);
        return ResponseEntity.noContent().build();
    }
}
