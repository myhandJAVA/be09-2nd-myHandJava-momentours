package com.myhandjava.momentours.schedule.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_schedule")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Schedule {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleNo;

    @Column
    private LocalDateTime scheduleStartDate;

    @Column
    private LocalDateTime scheduleEndDate;

    @Column
    private String scheduleTitle;

    @Column
    private String scheduleMemo;

    @Column
    private int coupleNo;

    @Column
    private int scheduleIsDeleted;

}
