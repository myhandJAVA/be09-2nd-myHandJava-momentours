package com.myhandjava.momentours.momentcourse.command.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FavoriteDTO {
    private int favoMomCourseNo;
    private int favoMomentNo;
    private int favoUserNo;
}
