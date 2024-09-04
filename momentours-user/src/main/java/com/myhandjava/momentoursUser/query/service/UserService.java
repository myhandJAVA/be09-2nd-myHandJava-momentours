package com.myhandjava.momentoursUser.query.service;

import com.myhandjava.momentoursUser.command.applicaiton.dto.*;
import com.myhandjava.momentoursUser.command.domain.vo.ResponseUserIdVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> findAllUsers();
    UserDTO findByUserEmail(String email);

    UserMyPageDTO viewMyPage(int userNo);

    UserInfoDTO searchUserWithId(String userEmail);

    UserInfoDTO searchByNickname(String userNickname);

    UserEntityDTO getUserPwdByEmail(String userEmail);


    ResponseUserIdVO getUserIdByPhone(String userPhone);
}
