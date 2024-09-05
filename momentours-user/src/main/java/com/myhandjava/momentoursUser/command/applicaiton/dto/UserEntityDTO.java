package com.myhandjava.momentoursUser.command.applicaiton.dto;


import com.myhandjava.momentoursUser.command.domain.aggregate.Gender;
import com.myhandjava.momentoursUser.command.domain.aggregate.MBTI;
import com.myhandjava.momentoursUser.command.domain.aggregate.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserEntityDTO {

    // user 도메인 기본 DTO
    private int userNo;
    private String userEmail;
    private String userPwd;
    private String userName;
    private String userNickname;
    private String userPhone;
    private LocalDateTime userBirth;
    private MBTI userMBTI;     //  enum type
    private UserRole userRole;     //  enum type
    private Gender userGender; //  enum type
    private LocalDateTime accessibleDate;
    private int userReportCount;
    private LocalDateTime userCreateAt;
    private LocalDateTime userUpdateAt;


    // 앞으로 로직에 필요한 생성자 만들기


}
