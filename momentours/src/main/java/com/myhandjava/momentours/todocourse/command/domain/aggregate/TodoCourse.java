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
    private int ToDoCourseNo;

    @Column
    private String ToDoCourseTitle;

    @Column
    @Enumerated(EnumType.STRING)
    private MomentCourseSort ToDoCourseSort;

    @Column
    private int ToDoCourseLike;

    @Column
    private LocalDateTime ToDoCourseCreateDate;

    @Column
    private LocalDateTime ToDoCourseUpdateDate;

    @Column
    private LocalDateTime ToDoCourseStartDate;

    @Column
    private LocalDateTime ToDoCourseEndDate;

    @Column
    private String ToDoCourseMemo;

    @Column
    private boolean ToDoCourseIsDeleted;

    @Column
    private int ToDoCourseCoupleNo;
}
