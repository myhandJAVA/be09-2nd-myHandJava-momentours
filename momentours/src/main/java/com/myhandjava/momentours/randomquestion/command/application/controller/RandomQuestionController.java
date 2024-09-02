package com.myhandjava.momentours.randomquestion.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.application.service.RandomQuestionServiceImpl;
import com.myhandjava.momentours.randomquestion.command.domain.vo.ModifyReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "randomQuestionCommandController")
@RequestMapping("/randomquestion")
@Slf4j
public class RandomQuestionController {
    private final RandomQuestionServiceImpl randomQuestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RandomQuestionController(RandomQuestionServiceImpl randomQuestionService, ModelMapper modelMapper) {
        this.randomQuestionService = randomQuestionService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/randomreply/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> removeRandomReply(@PathVariable int randomReplyNo) {
        randomQuestionService.removeRandomReply(randomReplyNo);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHttpStatus(HttpStatus.OK.value());
        responseMessage.setMessage("답변이 삭제되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PatchMapping(value = "/randomreply/{randomReplyNo}", consumes = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage> updateRandomReply(@PathVariable int randomReplyNo, @RequestBody ModifyReplyVO modifyRandomReply) {

        // 입력 데이터 유효성 검사 (옵션)
        if (modifyRandomReply == null || modifyRandomReply.getRandomReplyContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "Invalid input data", null));
        }

        RandomReplyDTO replyDTO = modelMapper.map(modifyRandomReply, RandomReplyDTO.class);
        randomQuestionService.updateRandomReply(randomReplyNo, replyDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyRandomReply", modifyRandomReply);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "답변이 수정되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

}
