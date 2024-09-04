package com.myhandjava.momentours.todocourse.command.domain.vo;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyTodoCourseVO {
    private String ToDoCourseTitle;
    private MomentCourseSort ToDoCourseSort;
    private LocalDateTime ToDoCourseStartDate;
    private LocalDateTime ToDoCourseEndDate;
    private String ToDoCourseMemo;
    private int ToDoCourseCoupleNo;
}
