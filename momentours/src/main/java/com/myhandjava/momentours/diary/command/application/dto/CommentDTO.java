package com.myhandjava.momentours.diary.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private int commentNo;
    private String commentContent;
    private LocalDateTime commentCreateDate;
    private LocalDateTime commentUpdateDate;
    private int commentUserNo;
    private int diaryNo;
    private boolean commentIsDeleted;
}
