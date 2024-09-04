package com.myhandjava.momentours.randomquestion.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RandomQuestionDTO {
    private LocalDateTime randQuesCreateDate;
    private String randQuesContent;
    private int randQuesReply;
    private int randQuesIsDeleted;
    private int randQuesCoupleNo;
}
