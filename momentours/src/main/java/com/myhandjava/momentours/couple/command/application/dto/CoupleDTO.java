package com.myhandjava.momentours.couple.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoupleDTO {
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
    private int coupleUserNo1;
    private int coupleUserNo2;
    private int coupleIsDeleted;
}
