package com.myhandjava.momentours.moment.command.application.controller;

import com.myhandjava.momentours.moment.command.application.dto.MomentDTO;
import com.myhandjava.momentours.moment.command.application.service.MomentService;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByCoupleNoVO;
import com.myhandjava.momentours.moment.command.domain.vo.ResponseFindMomentByMomentPublicVO;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    private MomentService momentService;

    @GetMapping("couple/{momentCoupleNo}")
    public List<ResponseFindMomentByCoupleNoVO> findMomentByMomentCoupleNo(@PathVariable int momentCoupleNo) {
        return momentService.findMomentByMomentCoupleNo(momentCoupleNo);
    }

    @GetMapping("/{momentPublic}")
    public List<ResponseFindMomentByMomentPublicVO> findMomentByMomentPublic(@PathVariable int momentPublic) {
        return momentService.findMomentByMomentPublic(momentPublic);
    }

    @PostMapping("/regist")
    public String registMoment(MomentDTO newMoment) {
        momentService.registMoment(newMoment);

        return "redirect:/moment/couple/{momentCoupleNo}";
    }

    @PutMapping("/updateByUser")
    public ResponseEntity<String> updateMoment(@RequestBody MomentDTO updatedMomentDTO) throws NotFoundException {
        momentService.updateMomentByTitleAndCoupleNo(
                updatedMomentDTO.getMomentNo(),
                updatedMomentDTO.getMomentCoupleNo(),
                updatedMomentDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{momentNo}/{momentCoupleNo}")
    public ResponseEntity<String> removeMoment(@PathVariable int momentNo,
                                               @PathVariable int momentCoupleNo) throws NotFoundException {
        momentService.removeMoment(momentNo, momentCoupleNo);

        return ResponseEntity.noContent().build();
    }
}
