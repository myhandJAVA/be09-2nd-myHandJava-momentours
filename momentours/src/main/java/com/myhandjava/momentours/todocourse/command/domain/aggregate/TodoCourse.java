package com.myhandjava.momentours.todocourse.command.domain.aggregate;

import com.myhandjava.momentours.momentcourse.command.domain.aggregate.MomentCourseSort;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "tb_todocourse")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TodoCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int toDoCourseNo;

    @Column
    private String toDoCourseTitle;

    @Column
    @Enumerated(EnumType.STRING)
    private MomentCourseSort toDoCourseSort;

    @Column
    private int toDoCourseLike;

    @Column
    private LocalDateTime toDoCourseCreateDate;

    @Column
    private LocalDateTime toDoCourseUpdateDate;

    @Column
    private LocalDateTime toDoCourseStartDate;

    @Column
    private LocalDateTime toDoCourseEndDate;

    @Column
    private String toDoCourseMemo;

    @Column
    private boolean toDoCourseIsDeleted;

    @Column
    private int toDoCourseCoupleNo;
}
