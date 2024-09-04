package com.myhandjava.momentours.randomquestion.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_randomreply")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomReply {

    @Id
    @Column(name = "random_reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randomReplyNo;

    @Column(name = "random_reply_content")
    private String randomReplyContent;

    @Column(name = "random_reply_user_no")
    private int randomReplyUserNo;

    @Column(name = "random_question_no")
    private int randomQuestionNo;

    @Column(name = "random_reply_is_deleted")
    private int randomReplyIsDeleted;

    @Column(name = "random_couple_no")
    private int randomCoupleNo;

}
