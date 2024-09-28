package com.myhandjava.momentours.schedule.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalendarDTO {
    private int id;
    private String title;
    private String content;
    private LocalDateTime start;
    private LocalDateTime end;
    private String contentType;
}
