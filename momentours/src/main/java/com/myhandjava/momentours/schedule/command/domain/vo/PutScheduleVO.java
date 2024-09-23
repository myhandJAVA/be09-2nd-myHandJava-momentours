package com.myhandjava.momentours.schedule.command.domain.vo;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PutScheduleVO {
    private int coupleNo;
    private int userNo;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private String scheduleTitle;
    private String scheduleMemo;
}
