package com.myhandjava.momentoursUser.query.service;

import com.myhandjava.momentoursUser.client.MomentoursClient;
import com.myhandjava.momentoursUser.command.applicaiton.dto.*;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import com.myhandjava.momentoursUser.command.domain.vo.ResponseUserIdVO;
import com.myhandjava.momentoursUser.common.ResponseMessage;
import com.myhandjava.momentoursUser.query.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("userQueryService")
public class UserQueryServiceImpl implements UserQueryService {
    private UserMapper userMapper;
    private MomentoursClient momentoursClient;

    @Autowired
    private UserQueryServiceImpl(UserMapper userMapper,
                                 MomentoursClient momentoursClient) {
        this.userMapper = userMapper;
        this.momentoursClient =momentoursClient;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        /* 설명. 넘어온 email이 사용자가 입력한 id로써 eamil로 회원을 조회하는 쿼리 메소드 작성 */
        UserEntity loginUser = userMapper.findByUserEmail(userEmail);

        if (loginUser == null) {
            throw new UsernameNotFoundException(userEmail + " 이메일 아이디의 유저는 존재하지 않습니다.");
        }

        /* 설명. 사용자의 권한들을 가져왔다는 가정 */
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginUser.getUserRole().toString()));

        return new User(loginUser.getUserEmail(), loginUser.getUserPwd(),
                true, true, true, true,
                grantedAuthorities);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntityList = userMapper.findAllUser();
        List<UserDTO> userDTOList = userEntityList.stream().map(
                userEntity -> entityToDTO(userEntity)
        ).collect(Collectors.toList());
        return userDTOList;
    }

    private UserDTO entityToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setBirth(userEntity.getUserBirth());
        userDTO.setEmail(userEntity.getUserEmail());
        userDTO.setGender(userEntity.getUserGender());
        userDTO.setMbti(userEntity.getUserMbti());
        userDTO.setName(userEntity.getUserName());
        userDTO.setNickname(userEntity.getUserNickname());
        userDTO.setPhone(userEntity.getUserPhone());
        userDTO.setUserRole(userEntity.getUserRole());
        userDTO.setUserPartnerNo(userEntity.getUserPartnerNo());
        return userDTO;
    }

    public UserDTO findByUserEmail(String email){
        UserEntity foundUser = userMapper.findByUserEmail(email);
        UserDTO userDTO = entityToDTO(foundUser);
        return userDTO;
    }

    @Override
    public UserMyPageDTO viewMyPage(int userNo) {
        int coupleNo = userMapper.findUserCoupleNoByUserNo(userNo);
        ResponseEntity<ResponseMessage> scheduleResponse = momentoursClient.findAllSchedule(coupleNo);

        UserMyPageDTO userMyPageDTO = new UserMyPageDTO();
        userMyPageDTO.setCoupleNo(coupleNo);
        userMyPageDTO.setUserNo(userNo);
        userMyPageDTO.setScheduleList((List<ScheduleDTO>)scheduleResponse.getBody().getResult().get("ScheduleList"));
        return userMyPageDTO;
    }

    @Override
    public UserInfoDTO searchUserWithId(String userEmail) {
        // Email로 조회
        UserEntity user = userMapper.searchUserWithEmail(userEmail);

        UserInfoDTO returnUserInfo = UserToUserDTO(user);

        return returnUserInfo;    }

    @Override
    public UserInfoDTO searchByNickname(String userNickname) {
        // Nickname으로 조회
        UserEntity user = userMapper.searchUserWithNickname(userNickname);

        UserInfoDTO returnUserInfo = UserToUserDTO(user);

        return returnUserInfo;    }

    @Override
    public UserEntityDTO getUserPwdByEmail(String userEmail) {
        UserEntity user = userMapper.getUserPwd(userEmail);

        UserEntityDTO userPwd = setUserPwdToDTO(user);

        return userPwd;    }


    @Override
    public ResponseUserIdVO getUserIdByPhone(String userPhone) {
        UserEntity user = userMapper.getUserIdWithPhone(userPhone);

        ResponseUserIdVO Result = UserToResponseUserIdVO(user);

        return Result;    }

    @Override
    public UserDTO findByUserNo(int userNo) {
        UserEntity user = userMapper.findUserByUserNo(userNo);
        UserDTO userDTO = entityToDTO(user);
        return userDTO;
    }

    @Override
    public Map<String,Object> findPartnerByUserNo(int userNo) {
        UserEntity user = userMapper.findUserByUserNo(userNo);
        Map<String,Object> reusltMap = new HashMap<>();
        reusltMap.put("userNo",userNo);
        reusltMap.put("partnerNo",user.getUserPartnerNo());

        return reusltMap;
    }

    private ResponseUserIdVO UserToResponseUserIdVO(UserEntity user) {
        // getUserIdByPhone() 타입 변환용 메소드
        ResponseUserIdVO returnValue = new ResponseUserIdVO();
        returnValue.setUserEmail(user.getUserEmail());

        return returnValue;
    }

    private UserEntityDTO setUserPwdToDTO(UserEntity user) {
        // UserPwd set 메소드
        UserEntityDTO userPwdInfo = new UserEntityDTO();

        userPwdInfo.setUserPwd(user.getUserPwd());

        return userPwdInfo;
    }

    private UserEntityDTO setUserInfoToDTO(UserEntity user) {
        // For Admin 회원 정보 조회용 메소드
        UserEntityDTO userInfo = new UserEntityDTO();
        userInfo.setUserBirth(user.getUserBirth());
        userInfo.setUserEmail(user.getUserEmail());
        userInfo.setUserNo(user.getUserNo());
        userInfo.setUserName(user.getUserName());
        userInfo.setUserPhone(user.getUserPhone());
        userInfo.setUserRole(user.getUserRole());
        userInfo.setUserGender(user.getUserGender());
        userInfo.setUserMBTI(user.getUserMbti());
        userInfo.setUserNickname(user.getUserNickname());
        userInfo.setUserReportCount(user.getUserReportCount());
        userInfo.setAccessibleDate(user.getAccessibleDate());
        userInfo.setUserCreateAt(user.getUserCreateAt());
        userInfo.setUserUpdateAt(user.getUserUpdateAt());

        return userInfo;
    }
    private UserInfoDTO UserToUserDTO(UserEntity user) {
        /* 매핑용 메소드 */
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setUserNickname(user.getUserNickname());
        userInfoDTO.setUserMBTI(user.getUserMbti());
        userInfoDTO.setUserGender(user.getUserGender());

        return userInfoDTO;
    }
}
