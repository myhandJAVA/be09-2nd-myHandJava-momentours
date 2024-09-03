package com.myhandjava.momentours.couple.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "tb_couple")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Couple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couple_no")
    private int coupleNo;

    @Column(name = "couple_name")
    private String coupleName;

    @Column(name = "couple_photo")
    private String couplePhoto;

    @Column(name = "couple_start_date")
    private LocalDateTime coupleStartDate;

    @Column(name = "couple_user_no1")
    private int coupleUserNo1;

    @Column(name = "couple_user_no2")
    private int coupleUserNo2;

    @Column(name = "couple_is_deleted")
    private int coupleIsDeleted;
}
