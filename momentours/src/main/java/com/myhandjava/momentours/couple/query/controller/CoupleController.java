package com.myhandjava.momentours.couple.query.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import com.myhandjava.momentours.couple.query.service.CoupleService;
import com.myhandjava.momentours.couple.query.service.CoupleServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "CoupleQueryController")
@RequestMapping("/couple")
public class CoupleController {

    private final CoupleService coupleService;

    @Autowired
    public CoupleController(CoupleService coupleService) {
        this.coupleService = coupleService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage> findCouple(@RequestAttribute("claims") Claims getCoupleNo) {
        int coupleNo = Integer.parseInt(getCoupleNo.get("coupleNo", String.class));
        CoupleDTO selectedCouple = coupleService.findCoupleByCoupleNo(coupleNo);
        Map<String, Object> map = new HashMap<>();
        map.put("selectedCouple", selectedCouple);
        ResponseMessage responseMessage =
                new ResponseMessage(200, "커플 조회 성공", map);

        return ResponseEntity.ok(responseMessage);
    }
}
