package com.myhandjava.momentours.momentcourse.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_momentcourselocation")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Momcourselocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseLocationNo;

    @Column
    private int momCourseNo;

    @Column(nullable = true)
    private int momentNo;
}
