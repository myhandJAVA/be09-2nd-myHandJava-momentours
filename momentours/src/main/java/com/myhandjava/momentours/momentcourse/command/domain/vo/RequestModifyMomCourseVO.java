package com.myhandjava.momentours.momentcourse.command.domain.vo;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyMomCourseVO {
    private int coupleNo;
    private String momCourseTitle;
    private MomentCourseSort momCourseSort;
    private String momCourseMemo;
    private boolean momCoursePublic;
    private List<Integer> momentNos;
}
