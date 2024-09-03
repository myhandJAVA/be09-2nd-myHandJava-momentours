package com.myhandjava.momentoursUser.command.domain.vo;

import com.myhandjava.momentoursUser.command.domain.aggregate.Gender;
import com.myhandjava.momentoursUser.command.domain.aggregate.MBTI;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestUpdateUserVO {
    private String name;
    private String nickname;
    private String phone;
    private LocalDateTime birth;
    private MBTI mbti;
    private Gender gender;
}
