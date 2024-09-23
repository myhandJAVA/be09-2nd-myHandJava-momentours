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
public class RandomQuestionAndReplyController {

    private final RandomQuestionAndReplyCommandService randomQuestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionAndReplyController(@Qualifier("randomQuesCommandService") RandomQuestionAndReplyCommandService randomQuestionService, ModelMapper modelMapper) {
        this.randomQuestionService = randomQuestionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseMessage> findRandomQuestion(@RequestBody int coupleNo) {
        RandomQuestionDTO randomQuestion = randomQuestionService.findRandomQuestion(coupleNo);
        Map<String, Object> map = new HashMap<>();
        map.put("randomQuestion", randomQuestion);
        return ResponseEntity.ok(
                new ResponseMessage(200, "랜덤질문 반환 성공", map));
    }

    @PostMapping("/randomreply/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> removeRandomReply(@RequestBody int userNo,
                                                             @PathVariable int randomReplyNo) {
        randomQuestionService.removeRandomReply(randomReplyNo, userNo);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHttpStatus(HttpStatus.OK.value());
        responseMessage.setMessage("답변이 삭제되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping(value = "/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> modifyRandomReply(@PathVariable int randomReplyNo,
                                                             @RequestBody ModifyReplyVO modifyRandomReply) {
        RandomReplyDTO replyDTO = new RandomReplyDTO();
        replyDTO.setRandomReplyContent(modifyRandomReply.getRandomReplyContent());
        replyDTO.setRandomReplyUserNo(modifyRandomReply.getUserNo());
        randomQuestionService.modifyRandomReply(randomReplyNo, replyDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyRandomReply", modifyRandomReply);
        ResponseMessage responseMessage =
                new ResponseMessage(HttpStatus.OK.value(), "답변이 수정되었습니다.", responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/{randomQuestionNo}")
    public ResponseEntity<ResponseMessage> registRandomReply(@PathVariable int randomQuestionNo,
                                                             @RequestBody RegistRequestReplyVO registRequestReplyVO) throws Exception {
        RandomReplyDTO replyDTO = new RandomReplyDTO();
        replyDTO.setRandomReplyContent(registRequestReplyVO.getRandomReplyContent());
        replyDTO.setRandomCoupleNo(registRequestReplyVO.getCoupleNo());
        replyDTO.setRandomReplyUserNo(registRequestReplyVO.getUserNo());
        randomQuestionService.registRandomReply(randomQuestionNo, replyDTO);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("registRandomReply", replyDTO);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 등록되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
