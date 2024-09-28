package com.myhandjava.momentours.schedule.command.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class RegistScheduleVO {
    private int userNo;
    private int coupleNo;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
    private String scheduleTitle;
    private String scheduleMemo;
}
