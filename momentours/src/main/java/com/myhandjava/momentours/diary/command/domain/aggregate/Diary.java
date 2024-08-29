package com.myhandjava.momentours.diary.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "tb_diary")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class Diary {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diaryNo;

    @Column
    private String diaryContent;

    @Column
    private LocalDateTime diaryCreateDate;

    @Column
    private LocalDateTime diaryUpdateDate;

    @Column
    private int diaryUserNo;

    @Column
    private int coupleNo;

    @Column
    private int diaryIsDeleted;
}
