package com.myhandjava.momentours.schedule.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ScheduleDTO {
    private int scheduleNo;
    private java.util.Date scheduleStartDate;
    private java.util.Date scheduleEndDate;
    private String scheduleTitle;
    private String scheduleMemo;
    private int coupleNo;

//    public ScheduleDTO(){}
}
