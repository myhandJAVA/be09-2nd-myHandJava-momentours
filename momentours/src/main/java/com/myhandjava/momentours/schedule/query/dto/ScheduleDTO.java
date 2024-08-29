package com.myhandjava.momentours.schedule.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {
    private int coupleNo;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private String scheduleTitle;
    private String scheduleMemo;
    private int scheduleNo;
}
