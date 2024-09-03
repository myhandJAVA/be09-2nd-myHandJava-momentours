package com.myhandjava.momentoursUser.command.applicaiton.dto;

import com.myhandjava.momentoursUser.command.domain.aggregate.Gender;
import com.myhandjava.momentoursUser.command.domain.aggregate.MBTI;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String email;
    private String pwd;
    private String name;
    private String nickname;
    private String phone;
    private LocalDateTime birth;
    private MBTI mbti;
    private UserRole userRole;
    private Gender gender;
    private int userPartnerNo;
}
