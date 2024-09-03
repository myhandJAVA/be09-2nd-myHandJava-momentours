package com.myhandjava.momentours.momentcourse.command.application.dto;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class MomentCourseDTO {
    private int momCourseNo;
    private String momCourseTitle;
    private MomentCourseSort momCourseSort;
    private int momCourseLike;
    private LocalDateTime momCourseCreateDate;
    private LocalDateTime momCourseUpdateDate;
    private String momCourseMemo;
    private boolean momCoursePublic;
    private boolean momCourseIsDeleted;
    private int momCourseCoupleNo;

    private List<Integer> momentNos;
}
