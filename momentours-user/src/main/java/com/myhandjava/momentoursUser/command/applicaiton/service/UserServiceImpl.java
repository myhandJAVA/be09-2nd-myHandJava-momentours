package com.myhandjava.momentoursUser.command.applicaiton.service;

import com.myhandjava.momentoursUser.client.MomentoursClient;
import com.myhandjava.momentoursUser.command.applicaiton.dto.CoupleRegisterDTO;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import com.myhandjava.momentoursUser.command.applicaiton.dto.UserDTO;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserRole;
import com.myhandjava.momentoursUser.command.domain.repository.UserRepository;
import com.myhandjava.momentoursUser.common.ResponseMessage;
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
    private final MomentoursClient momentoursClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           MomentoursClient momentoursClient){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.momentoursClient = momentoursClient;
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
        if(updateUser.getUserPartnerNo()!=0) user.setUserPartnerNo(updateUser.getUserPartnerNo());
        user.setUserUpdateAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteUser(int userNo){
        UserEntity user = userRepository.findById(userNo).orElseThrow(IllegalArgumentException::new);
        user.setUserRole(UserRole.ROLE_LEAVE);
    }

    @Override
    @Transactional
    public int makeCouple(int userNo,
                          UserDTO userDTO) {
        UserEntity you = userRepository.findByUserEmail(userDTO.getEmail());
        UserEntity me = userRepository.findById(userNo).orElseThrow(IllegalArgumentException::new);

        me.setUserPartnerNo(you.getUserNo());
        log.info(you.getUserPartnerNo());   //12번 회원의 파트너 번호
        log.info(me.getUserPartnerNo());  //13번 회원의 파트너 번호
        int coupleNo = 0;
        if(you.getUserPartnerNo() == me.getUserNo() && me.getUserPartnerNo() == you.getUserNo()){
            me.setUserRole(UserRole.ROLE_COUPLE);
            you.setUserRole(UserRole.ROLE_COUPLE);
            CoupleRegisterDTO coupleRegisterDTO = new CoupleRegisterDTO();
            coupleRegisterDTO.setUserNo1(userNo);  // 13;
            coupleRegisterDTO.setUserNo2(you.getUserNo());  // 12
            ResponseMessage response = momentoursClient.registerCouple(coupleRegisterDTO).getBody();
            coupleNo = (Integer) response.getResult().get("coupleNo");
        }
        return coupleNo;
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
