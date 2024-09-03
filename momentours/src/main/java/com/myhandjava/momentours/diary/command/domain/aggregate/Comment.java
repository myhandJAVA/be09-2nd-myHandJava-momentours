package com.myhandjava.momentours.diary.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_comment")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Comment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;

    @Column
    private String commentContent;

    @Column
    private LocalDateTime commentCreateDate;

    @Column
    private LocalDateTime commentUpdateDate;

    @Column
    private int commentUserNo;

    @Column
    private int diaryNo;

    @Column
    private boolean commentIsDeleted;
}
