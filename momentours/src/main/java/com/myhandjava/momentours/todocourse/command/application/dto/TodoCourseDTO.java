package com.myhandjava.momentours.todocourse.command.application.dto;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class TodoCourseDTO {
//    private int ToDoCourseNo;
    private String toDoCourseTitle;
    private MomentCourseSort toDoCourseSort;
    private LocalDateTime toDoCourseCreateDate;
    private LocalDateTime toDoCourseUpdateDate;
    private LocalDateTime toDoCourseStartDate;
    private LocalDateTime toDoCourseEndDate;
    private String toDoCourseMemo;
    private boolean toDoCourseIsDeleted;
    private int toDoCourseCoupleNo;

    private List<Integer> todoNos;

}
