package com.myhandjava.momentours.couple.query.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.query.dto.CoupleDTO;
import com.myhandjava.momentours.couple.query.service.CoupleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "CoupleQueryController")
@RequestMapping("/couple")
public class CoupleController {

    private final CoupleServiceImpl coupleService;

    @Autowired
    public CoupleController(CoupleServiceImpl coupleService) {
        this.coupleService = coupleService;
    }

    @GetMapping("/{coupleNo}")
    public ResponseEntity<ResponseMessage> findCouple(@PathVariable int coupleNo) {
        CoupleDTO selectedCouple = coupleService.findCoupleByCoupleNo(coupleNo);
        Map<String, Object> map = new HashMap<>();
        map.put("selectedCouple", selectedCouple);
        ResponseMessage responseMessage =
                new ResponseMessage(200, "커플 조회 성공", map);

        return ResponseEntity.ok(responseMessage);
    }
}
