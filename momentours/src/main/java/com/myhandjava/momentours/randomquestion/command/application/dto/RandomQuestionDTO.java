package com.myhandjava.momentours.randomquestion.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RandomQuestionDTO {
    private LocalDateTime randQuesCreateDate;
    private String randQuesContent;
    private int randQuesReply;
    private int randQuesIsDeleted;
    private int randQuesCoupleNo;
}
