package com.myhandjava.momentours.randomquestion.query.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionAndReplyDTO;
import com.myhandjava.momentours.randomquestion.query.dto.RandomQuestionDTO;

import com.myhandjava.momentours.randomquestion.query.service.RandomQuestionAndReplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(value = "randomQuestionAndReplyQueryController")
@RequestMapping("/randomquestandreply")
public class RandomQuestionAndReplyController {

    private final RandomQuestionAndReplyServiceImpl randomQuestionService;

    @Autowired
    public RandomQuestionAndReplyController(RandomQuestionAndReplyServiceImpl randomQuestionService) {
        this.randomQuestionService = randomQuestionService;
    }

    @GetMapping("/randomquestion/{coupleNo}")
    public ResponseEntity<ResponseMessage> getAllRandomQuestion(@PathVariable int coupleNo) {
        List<RandomQuestionDTO> result =
                randomQuestionService.findAllRandomQuestionByCoupleNo(coupleNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", result);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "조회 성공.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage> getAllRandomQuestionAndReply(@RequestParam int coupleNo, @RequestParam int userNo) {
        List<RandomQuestionAndReplyDTO> result =
                randomQuestionService.findAllRandomQuestionAndRepliesByUserNoAndCoupleNo(coupleNo, userNo);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("result", result);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "조회 성공.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
