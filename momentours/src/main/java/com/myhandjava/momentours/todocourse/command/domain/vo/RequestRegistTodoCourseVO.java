package com.myhandjava.momentours.todocourse.command.domain.vo;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistTodoCourseVO {
    private String toDoCourseTitle;
    private MomentCourseSort toDoCourseSort;
    private LocalDateTime toDoCourseStartDate;
    private LocalDateTime toDoCourseEndDate;
    private String toDoCourseMemo;
    private int toDoCourseCoupleNo;
    private List<Integer> todoNos;
}
