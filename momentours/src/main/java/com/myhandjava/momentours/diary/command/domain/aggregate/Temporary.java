package com.myhandjava.momentours.diary.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_diarytempsave")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Temporary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diaryTempSaveNo;

    @Column
    private LocalDateTime diaryTempSaveDate;

    @Column
    private int diaryNo;
}
