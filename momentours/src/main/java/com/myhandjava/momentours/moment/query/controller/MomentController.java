package com.myhandjava.momentours.moment.query.controller;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;
import com.myhandjava.momentours.moment.query.service.MomentService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController("queryMomentController")
@RequestMapping("/moment")
public class MomentController {

    private final MomentService momentService;

    @Autowired
    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }

    @GetMapping("/all")
    public String findMoment(@RequestAttribute("coupleNo") Claims coupleNo) {

        int momentCoupleNo = Integer.parseInt(coupleNo.getAudience());
        MomentDTO momentDTO = new MomentDTO();

        momentDTO.setMomentCoupleNo(momentCoupleNo);
        List<MomentDTO> momentDTOList = momentService.findAllMomentByCoupleNo(momentDTO);

        return momentDTOList.toString();

    }



}
