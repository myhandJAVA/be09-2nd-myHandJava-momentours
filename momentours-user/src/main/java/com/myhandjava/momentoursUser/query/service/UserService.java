package com.myhandjava.momentoursUser.query.service;

import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserMyPageDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> findAllUsers();
    UserDTO findByUserEmail(String email);

    UserMyPageDTO viewMyPage(int userNo);
}
