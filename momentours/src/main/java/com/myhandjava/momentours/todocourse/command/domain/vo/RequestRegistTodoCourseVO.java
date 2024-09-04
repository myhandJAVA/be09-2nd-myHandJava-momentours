package com.myhandjava.momentours.todocourse.command.domain.vo;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistTodoCourseVO {
    private String ToDoCourseTiTle;
    private MomentCourseSort ToDoCourseSort;
    private LocalDateTime ToDoCourseCreateTime;
    private LocalDateTime ToDoCourseStartTime;
    private LocalDateTime ToDoCourseEndTime;
    private String ToDoCourseMemo;
    private int ToDoCourseCoupleNo;
}
