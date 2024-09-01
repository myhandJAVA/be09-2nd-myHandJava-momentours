package com.myhandjava.momentours.diary.query.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DiaryDTO {
    private int diaryNo;
    private String diaryContent;
    private Date diaryCreateDate;
    private Date diaryUpdateDate;
    private int diaryUserNo;
    private int coupleNo;
    private int coupleDeleted;

}



