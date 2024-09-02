package com.myhandjava.momentours.todocourse.query.dto;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TodoCourseDTO {
    private int ToDoCourseNo;
    private String ToDoCourseTitle;
    private MomentCourseSort ToDoCourseSort;
    private int ToDoCourseLike;
    private LocalDateTime ToDoCourseCreateDate;
    private LocalDateTime ToDoCourseUpdateDate;
    private LocalDateTime ToDoCourseStartDate;
    private LocalDateTime ToDoCourseEndDate;
    private String ToDoCourseMemo;
    private boolean ToDoCourseIsDeleted;
    private int ToDoCourseCoupleNo;
}
