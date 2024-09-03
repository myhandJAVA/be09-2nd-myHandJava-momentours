package com.myhandjava.momentoursUser.query.repository;


import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity findByUserEmail(String userEmail);

    List<UserEntity> findAllUser();
}
