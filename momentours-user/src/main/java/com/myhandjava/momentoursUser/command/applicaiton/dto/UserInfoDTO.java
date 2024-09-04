package com.myhandjava.momentoursUser.command.applicaiton.dto;


import com.myhandjava.momentoursUser.command.domain.aggregate.Gender;
import com.myhandjava.momentoursUser.command.domain.aggregate.MBTI;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserInfoDTO {
    // UserInfo 조회시 반환용 DTO
    private String userNickname;
    private MBTI userMBTI;     //  enum type
    private Gender userGender;
}
