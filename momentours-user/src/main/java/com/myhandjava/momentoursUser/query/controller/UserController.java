package com.myhandjava.momentoursUser.query.controller;

import com.myhandjava.momentoursUser.client.MomentoursClient;
import com.myhandjava.momentoursUser.command.applicaiton.dto.*;
import com.myhandjava.momentoursUser.command.domain.vo.ResponseUserIdVO;
import com.myhandjava.momentoursUser.command.domain.vo.ResponseUserPwdVO;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import com.myhandjava.momentoursUser.query.service.UserQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserQueryService userQueryService;
    private MomentoursClient momentoursClient;

    public UserController(UserQueryService userQueryService,
                          MomentoursClient momentoursClient){
        this.userQueryService = userQueryService;
        this.momentoursClient = momentoursClient;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        List<UserDTO> userDTOList = userQueryService.findAllUsers();

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("users",userDTOList);
        ResponseMessage responseMessage = new ResponseMessage(200,"전체 회원 조회 성공",responseMap);
        return ResponseEntity.ok().body(responseMessage);
    }
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findByUserNo(@PathVariable int userNo){
        UserDTO userDTO = userQueryService.findByUserNo(userNo);
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("user",userDTO);
        ResponseMessage responseMessage = new ResponseMessage(200,"회원 조회 성공",responseMap);
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/users/{userNo}/partner")
    public ResponseEntity<ResponseMessage> findPartnerByUserNo(@PathVariable int userNo){
        Map<String,Object> resultMap =  userQueryService.findPartnerByUserNo(userNo);
        ResponseMessage responseMessage = new ResponseMessage(200, "파트너 회원 번호 반환", resultMap);
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/users/{userNo}/mypage")
    public ResponseEntity<ResponseMessage> viewMyPage(@PathVariable int userNo){
        UserMyPageDTO userMyPageDTO = userQueryService.viewMyPage(userNo);
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("userMyPage",userMyPageDTO);
        ResponseMessage responseMessage = new ResponseMessage(200,"마이페이지-스케줄 조회",responseMap);
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/searchById/{userEmail}")
    public String findUserById(@PathVariable String userEmail) {
        // user id로 정보 조회
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo = userQueryService.searchUserWithId(userEmail);

        return userInfo.toString();
    }

    @GetMapping("/searchByNickname/{userNickname}")
    public String searchUserByNickname(@PathVariable String userNickname) {
        // usernickname으로 정보 조회
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo = userQueryService.searchByNickname(userNickname);

        return userInfo.toString();
    }


    @GetMapping("/findUserPwdByEmail/{userEmail}")
    public String findUserPwdByEmail(@PathVariable String userEmail) {
        // Email로 Pwd 조회
        UserEntityDTO userInfo = new UserEntityDTO();
        userInfo = userQueryService.getUserPwdByEmail(userEmail);

        ResponseUserPwdVO userPwdVO = new ResponseUserPwdVO();
        userPwdVO.setUserPwd(userInfo.getUserPwd());

        // Pwd 마스킹 처리
        String maskedPwd = userInfo.getUserPwd().substring(0, 4) + "********";
        userPwdVO.setUserPwd(maskedPwd);

        return userPwdVO.toString();
    }



    @GetMapping("/findUserIdByPhone/{userPhone}")
    public ResponseUserIdVO findUserIdByPhone(@PathVariable String userPhone) {
        // 전화번호로 아이디 찾기
        ResponseUserIdVO responseUserIdVO = userQueryService.getUserIdByPhone(userPhone);

        return responseUserIdVO;
    }


}
