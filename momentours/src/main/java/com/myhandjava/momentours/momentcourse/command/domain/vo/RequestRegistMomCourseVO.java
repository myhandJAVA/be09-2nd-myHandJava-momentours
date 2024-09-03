package com.myhandjava.momentours.momentcourse.command.domain.vo;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistMomCourseVO {
    private String momCourseTitle;
    private MomentCourseSort momCourseSort;
    private int momCourseLike;
    private String momCourseMemo;
    private boolean momCoursePublic;
    private int momCourseCoupleNo;
    private List<Integer> momentNos;
}
