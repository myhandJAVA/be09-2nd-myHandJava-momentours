package com.myhandjava.momentours.diary.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RequestRegistDiaryVO {
    private String diaryContent;
    private LocalDateTime diaryCreateDate;
    private int diaryUserNo;
    private int coupleNo;
}
