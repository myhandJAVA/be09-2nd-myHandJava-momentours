package com.myhandjava.momentours.diary.command.domain.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyCommentVO {
    private int commentUserNo;
    private String commentContent;
}
