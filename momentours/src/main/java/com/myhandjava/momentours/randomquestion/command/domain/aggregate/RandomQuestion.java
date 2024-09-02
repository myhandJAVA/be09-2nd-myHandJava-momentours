package com.myhandjava.momentours.randomquestion.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Table(name = "tb_randomquestion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RandomQuestion {

    @Id
    @Column(name = "rand_ques_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randQuesNo;

    @Column(name = "rand_ques_createDate")
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
