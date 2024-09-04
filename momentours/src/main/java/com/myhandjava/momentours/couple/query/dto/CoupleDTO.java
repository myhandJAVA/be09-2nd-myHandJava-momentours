package com.myhandjava.momentours.couple.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CoupleDTO {
    private int coupleNo;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
    private int coupleUserNo1;
    private int coupleUserNo2;
    private int coupleIsDeleted;
}
