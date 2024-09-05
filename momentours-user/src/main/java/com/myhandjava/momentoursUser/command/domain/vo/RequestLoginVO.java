package com.myhandjava.momentoursUser.command.domain.vo;

import lombok.Data;

@Data
public class RequestLoginVO {
    private String userEmail;
    private String userPwd;
}
