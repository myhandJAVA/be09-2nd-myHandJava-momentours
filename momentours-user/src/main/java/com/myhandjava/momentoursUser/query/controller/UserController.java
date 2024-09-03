package com.myhandjava.momentoursUser.query.controller;

import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
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

    public UserController(UserService userService){
        this.userService = userService;
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
        return ResponseEntity.ok().body(null);
    }


}
