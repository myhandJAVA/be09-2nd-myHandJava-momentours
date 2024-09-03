package com.myhandjava.momentoursUser.command.applicaiton.service;

import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserRole;
import com.myhandjava.momentoursUser.command.domain.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service(value = "userCommandService")
@Log4j2
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public void registUser(UserDTO userDTO) {
        UserEntity user = dtoToEntity(userDTO);
        user.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getPwd()));
        user.setUserCreateAt(LocalDateTime.now());
        user.setUserUpdateAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(int userNo, UserDTO updateUser) {
        UserEntity user = userRepository.findById(userNo).orElseThrow(IllegalArgumentException::new);

        if(updateUser.getName()!=null) user.setUserName(updateUser.getName());
        if(updateUser.getNickname()!=null) user.setUserNickname(updateUser.getNickname());
        if(updateUser.getPhone()!=null) user.setUserPhone(updateUser.getPhone());
        if(updateUser.getBirth()!=null) user.setUserBirth(updateUser.getBirth());
        if(updateUser.getMbti()!=null) user.setUserMbti(updateUser.getMbti());
        if(updateUser.getGender()!=null) user.setUserGender(updateUser.getGender());
        user.setUserUpdateAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteUser(int userNo){
        UserEntity user = userRepository.findById(userNo).orElseThrow(IllegalArgumentException::new);
        user.setUserRole(UserRole.ROLE_LEAVE);
    }

    private UserEntity dtoToEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUserBirth(userDTO.getBirth());
        user.setUserEmail(userDTO.getEmail());
        user.setUserGender(userDTO.getGender());
        user.setUserMbti(userDTO.getMbti());
        user.setUserName(userDTO.getName());
        user.setUserNickname(userDTO.getNickname());
        user.setUserPhone(userDTO.getPhone());
        user.setUserRole(userDTO.getUserRole());
        return user;
    }

}
