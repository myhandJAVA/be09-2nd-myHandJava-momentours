package com.myhandjava.momentoursUser.command.domain.repository;

import com.myhandjava.momentoursUser.command.domain.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserEmail(String email);
}
