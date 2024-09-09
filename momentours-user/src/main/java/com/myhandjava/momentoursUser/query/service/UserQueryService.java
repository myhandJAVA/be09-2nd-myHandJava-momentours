package com.myhandjava.momentoursUser.query.service;

import com.myhandjava.momentoursUser.command.applicaiton.dto.*;
import com.myhandjava.momentoursUser.command.domain.vo.ResponseUserIdVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserQueryService extends UserDetailsService {
    List<UserDTO> findAllUsers();
    UserDTO findByUserEmail(String email);

    UserMyPageDTO viewMyPage(int userNo);

    UserInfoDTO searchUserWithId(String userEmail);

    UserInfoDTO searchByNickname(String userNickname);

    UserEntityDTO getUserPwdByEmail(String userEmail);


    ResponseUserIdVO getUserIdByPhone(String userPhone);

    UserDTO findByUserNo(int userNo);

    Map<String,Object> findPartnerByUserNo(int userNo);
}
