package com.myhandjava.momentours.moment.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_moment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moment_no")
    private int momentNo;

    @Column(name = "moment_title")
    private String momentTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "moment_category")
    private MomentCategory momentCategory;

    @Column(name = "moment_content")
    private String momentContent;

    @Column(name = "moment_create_date")
    private LocalDateTime momentCreateDate;

    @Column(name = "moment_update_date")
    private LocalDateTime momentUpdateDate;

    /* 설명. 추후 ArgumentConverter를 구현해 boolean으로 받을 수도 있겠음 */
    @Column(name = "moment_public")
    private int momentPublic;

    @Column(name = "moment_like")
    private int momentLike;

    @Column(name = "moment_view")
    private int momentView;

    @Column(name = "moment_couple_No")
    private int momentCoupleNo;

    /* 설명. 추후 ArgumentConverter를 구현해 boolean으로 받을 수도 있겠음 */
    @Column(name = "moment_is_deleted")
    private int momentIsDeleted;

    @Column(name = "moment_longitude")
    private double momentLongitude;

    @Column(name = "moment_latitude")
    private double momentLatitude;

    @Column(name = "moment_address")
    private String momentAddress;

    @Column(name = "moment_location_name")
    private String momentLocationName;

}
