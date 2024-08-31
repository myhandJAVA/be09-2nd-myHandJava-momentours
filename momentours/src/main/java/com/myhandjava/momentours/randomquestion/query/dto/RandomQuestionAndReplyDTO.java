package com.myhandjava.momentours.randomquestion.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomQuestionAndReplyDTO {
    private RandomQuestionDTO question;
    private RandomReplyDTO userReply;
    private RandomReplyDTO partnerReply;
    private boolean canViewPartnerReply;
}
