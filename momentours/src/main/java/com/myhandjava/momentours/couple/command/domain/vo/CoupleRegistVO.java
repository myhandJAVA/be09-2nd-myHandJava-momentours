package com.myhandjava.momentours.couple.command.domain.vo;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoupleRegistVO {
    private int coupleNo;
    private String coupleName;
    private String couplePhoto;
    private LocalDateTime coupleStartDate;
}
