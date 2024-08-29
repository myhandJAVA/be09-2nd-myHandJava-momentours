package com.myhandjava.momentours.schedule.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Schedule {
    @Id
    @Column
    private int scheduleNo;

    @Column
    private java.util.Date scheduleStartDate;

    @Column
    private java.util.Date scheduleEndDate;

    @Column
    private String scheduleTitle;

    @Column
    private String scheduleMemo;

    @Column
    private int coupleNo;

}
