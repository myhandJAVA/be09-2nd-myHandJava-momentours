package com.myhandjava.momentours.todocourse.command.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TodocourselocationDTO {
    private int todoCourseLocationNo;
    private int todoCourseNo;
    private int locationNo;
}
