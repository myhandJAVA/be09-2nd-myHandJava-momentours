package com.myhandjava.momentours.randomquestion.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.application.service.RandomQuestionAndReplyCommandService;
import com.myhandjava.momentours.randomquestion.command.domain.vo.ModifyReplyVO;
import com.myhandjava.momentours.randomquestion.command.domain.vo.RegistRequestReplyVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "randomQuestionCommandController")
@RequestMapping("/randomquestion")
@Slf4j
public class RandomQuestionAndReplyController {

    private final RandomQuestionAndReplyCommandService randomQuestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionAndReplyController(@Qualifier("randomQuesCommandService") RandomQuestionAndReplyCommandService randomQuestionService, ModelMapper modelMapper) {
        this.randomQuestionService = randomQuestionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseMessage> findRandomQuestion(@RequestAttribute("claims")Claims claims) {
        int coupleNo = (Integer) claims.get("coupleNo");
        RandomQuestionDTO randomQuestion = randomQuestionService.findRandomQuestion(coupleNo);
        Map<String, Object> map = new HashMap<>();
        map.put("randomQuestion", randomQuestion);

        return ResponseEntity.ok(
                new ResponseMessage(200, "랜덤질문 반환 성공", map));
    }

    @PostMapping("/randomreply/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> removeRandomReply(@RequestAttribute("claims")Claims claims,
                                                             @PathVariable int randomReplyNo) {
        int userNo = (Integer) claims.get("userNo");
        randomQuestionService.removeRandomReply(randomReplyNo, userNo);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHttpStatus(HttpStatus.OK.value());
        responseMessage.setMessage("답변이 삭제되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping(value = "/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> modifyRandomReply(@PathVariable int randomReplyNo,
                                                             @RequestBody ModifyReplyVO modifyRandomReply,
                                                             @RequestAttribute("claims") Claims claims) {
        int userNo = (Integer) claims.get("userNo");
        if (modifyRandomReply == null || modifyRandomReply.getRandomReplyContent() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "잘못된 데이터 형식입니다", null));
        RandomReplyDTO replyDTO = modelMapper.map(modifyRandomReply, RandomReplyDTO.class);
        randomQuestionService.modifyRandomReply(userNo, randomReplyNo, replyDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyRandomReply", modifyRandomReply);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 수정되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{randomQuestionNo}")
    public ResponseEntity<ResponseMessage> registRandomReply(
            @PathVariable int randomQuestionNo,
            @RequestBody RegistRequestReplyVO registRequestReplyVO,
            @RequestAttribute("claims") Claims claims) throws Exception {
        RandomReplyDTO replyDTO = new RandomReplyDTO();
        replyDTO.setRandomReplyContent(registRequestReplyVO.getRandomReplyContent());
        int coupleNo = (Integer) claims.get("coupleNo");
        int userNo = (Integer) claims.get("userNo");
        randomQuestionService.registRandomReply(coupleNo, userNo, randomQuestionNo, replyDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("registRandomReply", replyDTO);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 등록되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
