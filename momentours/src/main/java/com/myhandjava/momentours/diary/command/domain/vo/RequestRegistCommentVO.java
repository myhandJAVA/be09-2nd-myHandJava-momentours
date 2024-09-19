package com.myhandjava.momentours.diary.command.domain.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistCommentVO {
    private int commentUserNo;
    private String commentContent;
    private int diaryNo;
}
