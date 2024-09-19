package com.myhandjava.momentours.schedule.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindScheduleDTO {
    private int userNo;
    private int coupleNo;
    private int scheduleNo;

}
