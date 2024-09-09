package com.myhandjava.momentoursUser.command.applicaiton.controller;

import com.myhandjava.momentoursUser.command.domain.vo.RequestCoupleVO;
import com.myhandjava.momentoursUser.command.domain.vo.RequestUpdateUserVO;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.applicaiton.service.UserService;
import com.myhandjava.momentoursUser.command.domain.vo.RequestRegistUserVO;
import feign.Response;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController(value = "commandUserController")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env,
                          UserService userService){
        this.env = env;
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseMessage> registUser(@RequestBody RequestRegistUserVO newUser){

        UserDTO userDTO = requestResigtUserVOToUserDTO(newUser);
        userService.registUser(userDTO);

        ResponseMessage responseMessage = new ResponseMessage(201,"회원 가입 성공",null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> updateUser(@PathVariable int userNo,
                                                      @RequestBody RequestUpdateUserVO requestUpdateUserVO){
        UserDTO updateUser = requestUpdateUserVOToUserDTO(requestUpdateUserVO);
        userService.updateUser(userNo,updateUser);

        ResponseMessage responseMessage = new ResponseMessage(200,"회원 정보 수정 성공",null);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable int userNo){
        userService.deleteUser(userNo);
        ResponseMessage responseMessage = new ResponseMessage(200,"회원 탈퇴 성공",null);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PostMapping("/users/couple")
    public ResponseEntity<ResponseMessage> makeCouple(@RequestAttribute("claims") Claims claims,
                                                      @RequestBody RequestCoupleVO yourEmail){
        int userNo = (Integer) claims.get("userNo");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(yourEmail.getYourEmail());
        int coupleNo = userService.makeCouple(userNo,userDTO);
        Map<String, Object> result = new HashMap<>();
        if(coupleNo == 0) result.put("대기", "상대 회원이 아직 이메일을 입력하지 않았습니다.");
        else result.put("커플이 되었습니다!", "커플 번호: " + coupleNo);
        ResponseMessage responseMessage = new ResponseMessage(200,"커플 요청 성공",result);
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    private UserDTO requestUpdateUserVOToUserDTO(RequestUpdateUserVO requestUpdateUserVO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(requestUpdateUserVO.getName());
        userDTO.setNickname(requestUpdateUserVO.getNickname());
        userDTO.setPhone(requestUpdateUserVO.getPhone());
        userDTO.setBirth(requestUpdateUserVO.getBirth());
        userDTO.setMbti(requestUpdateUserVO.getMbti());
        userDTO.setGender(requestUpdateUserVO.getGender());
        return userDTO;
    }


    private UserDTO requestResigtUserVOToUserDTO(RequestRegistUserVO newMember) {
        UserDTO userDTO = new UserDTO();
        userDTO.setBirth(newMember.getBirth());
        userDTO.setEmail(newMember.getEmail());
        userDTO.setGender(newMember.getGender());
        userDTO.setMbti(newMember.getMbti());
        userDTO.setName(newMember.getName());
        userDTO.setNickname(newMember.getNickname());
        userDTO.setPhone(newMember.getPhone());
        userDTO.setPwd(newMember.getPwd());
        userDTO.setUserRole(newMember.getUserRole());

        return userDTO;
    }



}
