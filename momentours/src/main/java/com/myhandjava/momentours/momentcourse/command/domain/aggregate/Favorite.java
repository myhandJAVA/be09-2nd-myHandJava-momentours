package com.myhandjava.momentours.momentcourse.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tb_favorite")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favoNo;

    @Column
    private int favoMomCourseNo;

    @Column
    private int favoUserNo;
}
