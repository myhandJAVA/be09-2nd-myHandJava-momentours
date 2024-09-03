package com.myhandjava.momentoursUser.query.controller;

import com.myhandjava.momentoursUser.client.MomentoursClient;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserMyPageDTO;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import com.myhandjava.momentoursUser.query.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private MomentoursClient momentoursClient;

    public UserController(UserService userService,
                          MomentoursClient momentoursClient){
        this.userService = userService;
        this.momentoursClient = momentoursClient;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        List<UserDTO> userDTOList = userService.findAllUsers();

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("users",userDTOList);
        ResponseMessage responseMessage = new ResponseMessage(200,"전체 회원 조회 성공",responseMap);
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/users/{userNo}/mypage")
    public ResponseEntity<ResponseMessage> viewMyPage(@PathVariable int userNo){
        UserMyPageDTO userMyPageDTO = userService.viewMyPage(userNo);
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("userMyPage",userMyPageDTO);
        ResponseMessage responseMessage = new ResponseMessage(200,"마이페이지-스케줄 조회",responseMap);
        return ResponseEntity.ok().body(responseMessage);
    }


}
