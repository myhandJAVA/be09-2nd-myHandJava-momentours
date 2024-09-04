package com.myhandjava.momentours.randomquestion.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomQuestionDTO;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.application.service.RandomQuestionAndReplyServiceImpl;
import com.myhandjava.momentours.randomquestion.command.domain.vo.ModifyReplyVO;
import com.myhandjava.momentours.randomquestion.command.domain.vo.RegistRequestReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "randomQuestionAndReplyCommandController")
@RequestMapping("/randomquestandreply")
@Slf4j
public class RandomQuestionAndReplyController {

    private final RandomQuestionAndReplyServiceImpl randomQuestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionAndReplyController(RandomQuestionAndReplyServiceImpl randomQuestionService, ModelMapper modelMapper) {
        this.randomQuestionService = randomQuestionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/randomquestion/{coupleNo}/recent")
    public ResponseEntity<ResponseMessage> getOrGenerateRandomQuestion(@PathVariable int coupleNo) {
        RandomQuestionDTO randomQuestion = randomQuestionService.getCurrentRandomQuestion(coupleNo);
        Map<String, Object> map = new HashMap<>();
        map.put("randomQuestion", randomQuestion);

        return ResponseEntity.ok(
                new ResponseMessage(200, "랜덤질문 반환 성공", map));
    }

    @PostMapping("/randomreply/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> removeRandomReply(@PathVariable int randomReplyNo) {
        randomQuestionService.removeRandomReply(randomReplyNo);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHttpStatus(HttpStatus.OK.value());
        responseMessage.setMessage("답변이 삭제되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PatchMapping(value = "/randomreply/{randomReplyNo}", consumes = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage> updateRandomReply(@PathVariable int randomReplyNo, @RequestBody ModifyReplyVO modifyRandomReply) {
        if (modifyRandomReply == null || modifyRandomReply.getRandomReplyContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "잘못된 데이터 형식입니다", null));
        }

        RandomReplyDTO replyDTO = modelMapper.map(modifyRandomReply, RandomReplyDTO.class);
        randomQuestionService.updateRandomReply(randomReplyNo, replyDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyRandomReply", modifyRandomReply);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 수정되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/randomquestion/{randomQuestionNo}/reply")
    public ResponseEntity<ResponseMessage> registRandomReply(
            @PathVariable int randomQuestionNo,
            @RequestBody RegistRequestReplyVO registRequestReplyVO) {
        RandomReplyDTO replyDTO = modelMapper.map(registRequestReplyVO, RandomReplyDTO.class);
        replyDTO.setRandomQuestionNo(randomQuestionNo);

        randomQuestionService.registRandomReply(replyDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("registRandomReply", replyDTO);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 등록되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
