package com.myhandjava.momentours.momentcourse.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_momcourselocation")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Momcourselocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseLocationNo;

    @Column
    private int momCourseNo;

    @Column
    private int momentNo;
}
