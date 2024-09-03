package com.myhandjava.momentours.randomquestion.command.application.controller;

import com.myhandjava.momentours.common.ResponseMessage;
import com.myhandjava.momentours.randomquestion.command.application.dto.RandomReplyDTO;
import com.myhandjava.momentours.randomquestion.command.application.service.OpenAIService;
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
    private final OpenAIService openAIService;

    @Autowired
    public RandomQuestionAndReplyController(RandomQuestionAndReplyServiceImpl randomCommandService,
                                            ModelMapper modelMapper, OpenAIService openAIService) {
        this.randomQuestionService = randomCommandService;
        this.modelMapper = modelMapper;
        this.openAIService = openAIService;
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
                    .body(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "잘못된 데이터 형식입니다", null));
        }

        RandomReplyDTO replyDTO = modelMapper.map(modifyRandomReply, RandomReplyDTO.class);
        randomQuestionService.updateRandomReply(randomReplyNo, replyDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("modifyRandomReply", modifyRandomReply);
        ResponseMessage responseMessage =
                new ResponseMessage(HttpStatus.OK.value(), "답변이 수정되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
    @PostMapping("/randomquestion/{randomQuestionNo}/reply")
    public ResponseEntity<ResponseMessage> registRandomReply(
            @PathVariable int randomQuestionNo,
            @RequestBody RegistRequestReplyVO registRequestReplyVO) {
        // VO에 randomQuestionNo를 직접 설정
        RandomReplyDTO replyDTO = modelMapper.map(registRequestReplyVO, RandomReplyDTO.class);
        replyDTO.setRandomQuestionNo(randomQuestionNo); // PathVariable에서 받은 값 설정

        randomQuestionService.registRandomReply(replyDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("registRandomReply", replyDTO);
        ResponseMessage responseMessage =
                new ResponseMessage(HttpStatus.OK.value(), "답변이 등록되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/generate/{coupleNo}")
    public ResponseEntity<ResponseMessage> generateRandomReply(@PathVariable int coupleNo,
                                                               @RequestBody Map<String, Object> coupleInfo) {
        String randomQuestion = openAIService.generateQuestionForCouple(coupleInfo);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("randomQuestion", randomQuestion);
        ResponseMessage responseMessage =
                new ResponseMessage(HttpStatus.OK.value(), "커플별 랜덤질문이 생성되었습니다.", responseMap);

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
