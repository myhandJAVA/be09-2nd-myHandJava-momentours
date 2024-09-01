package com.myhandjava.momentours.randomquestion.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomReplyDTO {
    private int randomReplyNo;
    private String randomReplyContent;
    private int randomReplyUserNo;
    private int randomReplyQuestionNo;
    private int randomReplyIsDeleted;
    private int randomCoupleNo;
}
