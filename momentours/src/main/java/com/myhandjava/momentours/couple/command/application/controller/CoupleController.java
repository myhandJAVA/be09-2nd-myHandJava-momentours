package com.myhandjava.momentours.couple.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.couple.command.application.dto.CoupleDTO;
import com.myhandjava.momentours.couple.command.application.dto.CoupleRegisterDTO;
import com.myhandjava.momentours.couple.command.application.service.CoupleService;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleRegistVO;
import com.myhandjava.momentours.couple.command.domain.vo.CoupleUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Autowired
    public CoupleController(ModelMapper modelMapper, CoupleService coupleService) {
        this.modelMapper = modelMapper;
        this.coupleService = coupleService;
    }

    @Operation(summary = "Post Couple", description = "커플을 등록합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "커플 등록 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registNewCouple(@RequestBody CoupleRegisterDTO coupleRegisterDTO) {
        int userNo1 = coupleRegisterDTO.getUserNo1();
        int userNo2 = coupleRegisterDTO.getUserNo2();
        int coupleNo = coupleService.registCouple(userNo1, userNo2);
        Map<String, Object> result = new HashMap<>();
        result.put("coupleNo", coupleNo);
        return ResponseEntity.ok(new ResponseMessage(201, "커플 객체 생성 성공", result));
    }
    @Operation(summary = "Post Couple", description = "커플 정보를 입력합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "커플 정보 입력 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "40401", description = "해당 커플이 존재하지 않습니다.")
    })
    @PostMapping("/profile")
    public ResponseEntity<ResponseMessage> fillCoupleInfo(@RequestBody CoupleRegistVO coupleInfo) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        int coupleNo = coupleInfo.getCoupleNo();
        CoupleDTO coupleDTO = modelMapper.map(coupleInfo, CoupleDTO.class);
        coupleService.inputCoupleInfo(coupleNo, coupleDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("updatedCouple", coupleDTO);
        return ResponseEntity.ok(new ResponseMessage(200, "커플 정보 입력 성공", result));
    }

    @Operation(summary = "Put Couple", description = "커플 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "커플 정보 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "40401", description = "해당 커플이 존재하지 않습니다.")
    })
    @PutMapping("/profile/{coupleNo}")
    public ResponseEntity<ResponseMessage> updateCoupleInfo(@Parameter(description = "정보를 수정할 커플 번호", required = true, example = "2")
                                                                @PathVariable int coupleNo,
                                                                @RequestBody CoupleUpdateVO updateInfo) {
        CoupleDTO updatedCouple = new CoupleDTO();
        updatedCouple.setCouplePhoto(updateInfo.getCouplePhoto());
        updatedCouple.setCoupleName(updateInfo.getCoupleName());
        updatedCouple.setCoupleStartDate(updateInfo.getCoupleStartDate());
        coupleService.updateCouple(coupleNo, updatedCouple);
        Map<String, Object> map = new HashMap<>();
        map.put("updatedCouple", updatedCouple);
        return ResponseEntity.ok(new ResponseMessage(200, "커플 정보가 수정되었습니다.", map));
    }

    @Operation(summary = "Delete Couple", description = "커플을 삭제합니다")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "커플 삭제 성공"),
            @ApiResponse(responseCode = "40401", description = "해당 커플이 존재하지 않습니다.")
    })
    @DeleteMapping("/{coupleNo}")
    public ResponseEntity<ResponseMessage> deleteCoupleInfo(@Parameter(description = "삭제할 커플 번호", required = true, example = "1")
                                                                @PathVariable int coupleNo,
                                                                @RequestBody int userNo) {
        coupleService.deleteCouple(coupleNo, userNo);
        return ResponseEntity.noContent().build();
    }
}
