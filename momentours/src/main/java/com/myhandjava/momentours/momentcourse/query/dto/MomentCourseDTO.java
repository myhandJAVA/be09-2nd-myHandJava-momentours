package com.myhandjava.momentours.momentcourse.query.dto;

import com.myhandjava.momentours.moment.query.dto.MomentDTO;
import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MomentCourseDTO {
    private int momCourseNo;
    private String momCourseTitle;
    private MomentCourseSort momCourseSort;
    private int momCourseLike;
    private LocalDateTime momCourseCreateTime;
    private LocalDateTime momCourseUpdateTime;
    private String momCourseMemo;
    private boolean momCoursePublic;
    private boolean momCourseIsDeleted;
    private int momCourseCoupleNo;
    private boolean momCourseFavorite;

    private List<MomcourselocationDTO> momCourseLocations;
    private List<MomentDTO> moments;
}
