package com.myhandjava.momentoursUser.command.applicaiton.service;

import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;

public interface UserService {
    void registUser(UserDTO userDTO);

    void updateUser(int userNo, UserDTO updateUser);

    void deleteUser(int userNo);

    int makeCouple(int userNo, UserDTO userDTO);
}
