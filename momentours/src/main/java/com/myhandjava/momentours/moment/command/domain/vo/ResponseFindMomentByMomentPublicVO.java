package com.myhandjava.momentours.moment.command.domain.vo;

import com.myhandjava.momentours.moment.query.dto.MomentCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseFindMomentByMomentPublicVO {
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
