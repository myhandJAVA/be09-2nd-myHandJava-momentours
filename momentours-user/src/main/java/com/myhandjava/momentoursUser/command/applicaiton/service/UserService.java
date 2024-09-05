package com.myhandjava.momentoursUser.command.applicaiton.service;

import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.domain.vo.RequestCoupleVO;

public interface UserService {
    void registUser(UserDTO userDTO);

    void updateUser(int userNo, UserDTO updateUser);

    void deleteUser(int userNo);

    void makeCouple(int userNo, UserDTO userDTO);
}
