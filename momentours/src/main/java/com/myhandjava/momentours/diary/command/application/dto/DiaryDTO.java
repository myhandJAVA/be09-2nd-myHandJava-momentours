package com.myhandjava.momentours.diary.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class DiaryDTO {
    private String diaryContent;
    private LocalDateTime diaryCreateDate;
    private int diaryUserNo;
    private int coupleNo;
    private int diaryIsDeleted;
}
