package com.myhandjava.momentours.todocourse.command.application.dto;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TodoCourseDTO {
//    private int ToDoCourseNo;
    private String ToDoCourseTitle;
    private MomentCourseSort ToDoCourseSort;
    private LocalDateTime ToDoCourseCreateDate;
    private LocalDateTime ToDoCourseUpdateDate;
    private LocalDateTime ToDoCourseStartDate;
    private LocalDateTime ToDoCourseEndDate;
    private String ToDoCourseMemo;
    private boolean ToDoCourseIsDeleted;
    private int ToDoCourseCoupleNo;

}
