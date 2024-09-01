package com.myhandjava.momentours.randomquestion.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.application.service.RandomQuestionServiceImpl;
import com.myhandjava.momentours.randomquestion.command.domain.aggregate.RandomReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "randomQuestionCommandController")
@RequestMapping("/randomquestion")
@Slf4j
public class RandomQuestionController {
    private final RandomQuestionServiceImpl randomQuestionService;

    @Autowired
    public RandomQuestionController(RandomQuestionServiceImpl randomQuestionService) {
        this.randomQuestionService = randomQuestionService;
    }

    @PutMapping("/randomreply/{randomReplyNo}")
    public ResponseEntity<ResponseMessage> removeRandomReply(@PathVariable int randomReplyNo, @RequestParam int userNo) {
        randomQuestionService.removeRandomReply(randomReplyNo, userNo);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setHttpStatus(HttpStatus.OK.value());
        responseMessage.setMessage("답변이 삭제되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/randomreply/{randomReplyNo}")
    public RandomReply<ResponseMessage> updateRandomReply(@PathVariable int randomReplyNo, @ResponseBody RandomReplyDTO) {
        randomQuestionService = updateRandomReply()
    }
}
