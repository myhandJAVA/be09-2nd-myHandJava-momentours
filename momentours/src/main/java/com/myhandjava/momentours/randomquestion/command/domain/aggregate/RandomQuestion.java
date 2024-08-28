package com.myhandjava.momentours.randomquestion.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RandomQuestion {

    @Id
    @Column(name = "randQuesNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randquesno;

    @Column(name = "randQuesCreateDate")
    private LocalDateTime randquescreatedate;

    @Column(name = "randQuesContent")
    private String randquescontent;

    @Column(name = "randQuesReply")
    private int randquesreply;

    @Column(name = "randQuesIsDeleted")
    private int randquesisdeleted;

    @Column(name = "randQuesCoupleNo")
    private int randquescoupleno;
}
