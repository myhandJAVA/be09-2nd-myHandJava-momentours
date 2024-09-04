package com.myhandjava.momentoursUser.query.repository;


import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity findByUserEmail(String userEmail);

    List<UserEntity> findAllUser();

    Integer findUserCoupleNoByUserNo(int userNo);

    UserEntity searchUserWithEmail(String userEmail);

    UserEntity searchUserWithNickname(String userNickname);

    UserEntity getUserInfoWithEmail(String userEmail);

    UserEntity getUserPwd(String userEmail);

    UserEntity getUserIdWithPhone(String userPhone);
}
