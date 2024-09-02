package com.myhandjava.momentours.moment.command.application.dto;

import com.myhandjava.momentours.moment.query.dto.MomentCategory;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MomentDTO {
    private int momentNo;
    private String momentTitle;
    private MomentCategory momentCategory;
    private String momentContent;
    private LocalDateTime momentCreateDate;
    private LocalDateTime momentUpdateDate;
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
