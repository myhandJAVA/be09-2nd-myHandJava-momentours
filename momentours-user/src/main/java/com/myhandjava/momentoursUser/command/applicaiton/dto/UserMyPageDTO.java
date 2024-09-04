package com.myhandjava.momentoursUser.command.applicaiton.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserMyPageDTO {
    private int userNo;
    private int coupleNo;
    private List<ScheduleDTO> scheduleList;
}
