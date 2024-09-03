package com.myhandjava.momentours.diary.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TemporaryDTO {
    private int diaryTempSaveNo;
    private LocalDateTime diaryTempSaveDate;
    private int diaryNo;
}
