package com.myhandjava.momentours.momentcourse.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_momentcourse")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MomentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int momCourseNo;

    @Column
    private String momCourseTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "mom_course_sort")
    private MomentCourseSort momCourseSort;

    @Column
    private int momCourseLike;

    @Column
    private LocalDateTime momCourseCreateDate;

    @Column
    private LocalDateTime momCourseUpdateDate;

    @Column
    private String momCourseMemo;

    @Column
    private boolean momCoursePublic;

    @Column
    private boolean momCourseIsDeleted;

    @Column
    private int momCourseCoupleNo;
}
