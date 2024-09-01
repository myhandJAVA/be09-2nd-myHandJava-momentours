package com.myhandjava.momentours.moment.query.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MomentDTO {

    private String momentTitle;
    private MomentCategory momentCategory;
    private String momentContent;
    private Date momentCreateDate;
    private Date momentUpdateDate;
    private int momentPublic;
    private int momentLike;
    private int momentView;
    private int momentCoupleNo;
    private int momentIsDeleted;
    private double momentLongitude;
    private double momentLatitude;
    private String momentAddress;
    private String momentLocationName;

}
