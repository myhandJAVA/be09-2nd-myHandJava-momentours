package com.myhandjava.momentours.todocourse.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_todocourselocation")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Todocourselocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toDoCourseLocationNo;

    @Column
    private int toDoCourseNo;

    @Column
    private int locationNo;
}
