package com.myhandjava.momentours.randomquestion.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name = "tb_randomquestion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RandomQuestion {

    @Id
    @Column(name = "rand_ques_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randQuesNo;

    @Column(name = "rand_ques_create_date")
    private LocalDateTime randQuesCreateDate;

    @Column(name = "rand_ques_content")
    private String randQuesContent;

    @Column(name = "rand_ques_reply")
    private int randQuesReply;

    @Column(name = "rand_ques_is_deleted")
    private int randQuesIsDeleted;

    @Column(name = "rand_ques_couple_no")
    private int randQuesCoupleNo;
}
