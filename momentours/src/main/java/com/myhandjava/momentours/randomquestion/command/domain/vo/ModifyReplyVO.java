package com.myhandjava.momentours.randomquestion.command.domain.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ModifyReplyVO {
    private int userNo;
    private String randomReplyContent;
}
