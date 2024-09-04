package com.myhandjava.momentoursUser.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_user")
@Data
public class UserEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userNo;

    @Column
    private String userEmail;

    @Column
    private String userPwd;

    @Column
    private String userName;

    @Column
    private String userNickname;

    @Column
    private String userPhone;

    @Column
    private LocalDateTime userBirth;

    @Column
    @Enumerated(EnumType.STRING)
    private MBTI userMbti;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    private LocalDateTime accessibleDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender userGender;

    @Column
    private int userReportCount;

    @Column
    private LocalDateTime userCreateAt;

    @Column
    private LocalDateTime userUpdateAt;

    @Column
    private LocalDateTime userLastLogin;

    @Column
    private int userPartnerNo;

}
