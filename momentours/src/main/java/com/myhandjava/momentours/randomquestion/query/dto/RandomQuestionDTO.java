package com.myhandjava.momentours.randomquestion.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomQuestionDTO {
    private int randQuesNo;
    private LocalDateTime randQuesCreateDate;
    private String randQuesContent;
    private int randQuesReply;
    private int randQuesIsDeleted;
    private int randQuesCoupleNo;
}
