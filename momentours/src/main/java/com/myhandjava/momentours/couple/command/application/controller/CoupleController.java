package com.myhandjava.momentours.couple.command.application.controller;

import com.myhandjava.momentours.client.UserClient;
import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.application.service.CoupleServiceImpl;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleUpdateVO;
import com.myhandjava.momentours.couple.command.domain.vo.RequestSignCoupleVO;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/couple")
public class CoupleController {

    private final ModelMapper modelMapper;
    private final CoupleServiceImpl coupleService;
    private final UserClient userClient;

    @Autowired
    public CoupleController(ModelMapper modelMapper, CoupleServiceImpl coupleService, UserClient userClient) {
        this.modelMapper = modelMapper;
        this.coupleService = coupleService;
        this.userClient = userClient;
    }

    @PutMapping("")
    public ResponseEntity<?> registNewCouple(@RequestAttribute("claims") Claims claims) {
        int userNo1 = Integer.parseInt(claims.get("userNo", String.class));
        // 여기서 유저 번호로 유저 서비스에서 유저 테이블에 있는 파트너 번호 속성 조회해야 함
        ResponseEntity<ResponseMessage> response = userClient.findPartnerByUserNo(userNo1);
        Map<String, Object> map = new HashMap<>();
        Integer lastCoupleNo = coupleService.findLastCoupleNo();
        map.put("coupleNo", lastCoupleNo);
        return ResponseEntity.ok(
                new ResponseMessage(
                        200, "커플 번호 반환 성공", map
                )
        );
    }

    @PostMapping("/profile")
    public ResponseEntity<?> fillCoupleInfo(@RequestAttribute("claims") Claims claims,
                                            @RequestBody CoupleRegistVO coupleInfo) {
        int userNo1 = Integer.parseInt(claims.get("userNo", String.class));
        ResponseEntity<ResponseMessage> response = userClient.findPartnerByUserNo(userNo1);
        Map<String, Object> map = response.getBody().getResult();
        RequestSignCoupleVO requestSignCoupleVO = (RequestSignCoupleVO) map.get("userNos");
        int userNo2 = requestSignCoupleVO.getUserPartnerNo();

        // 입력된 커플 정보를 DTO로 변환
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CoupleDTO coupleDTO = modelMapper.map(coupleInfo, CoupleDTO.class);

        // 커플 정보를 저장
        coupleService.inputCoupleInfo(userNo1, userNo2, coupleDTO);

        Map<String, Object> result = new HashMap<>();
        map.put("updatedCouple", coupleDTO);
        return ResponseEntity.ok(
                new ResponseMessage(
                        200, "커플 등록이 성공적으로 되었습니다!", result
                )
        );
    }


@PatchMapping("")
    public ResponseEntity<ResponseMessage> updateCoupleInfo(@RequestBody CoupleUpdateVO updateInfo,
                                                            @RequestAttribute("claims") Claims coupleNo) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        int updateCoupleNo = Integer.parseInt(coupleNo.get("coupleNo", String.class));
        updateInfo.setCoupleNo(updateCoupleNo);
        CoupleDTO updatedCouple = modelMapper.map(updateInfo, CoupleDTO.class);

        coupleService.updateCouple(updateInfo.getCoupleNo(), updatedCouple);

        Map<String, Object> map = new HashMap<>();
        map.put("updatedCouple", updatedCouple);
        return ResponseEntity.ok(
                new ResponseMessage(
                        200, "커플 정보가 수정되었습니다.", map
                ));
    }

    @DeleteMapping("/{coupleNo}")
    public ResponseEntity<ResponseMessage> deleteCoupleInfo(@PathVariable int coupleNo) {
        coupleService.deleteCouple(coupleNo);

        return ResponseEntity.noContent().build();
    }
}
