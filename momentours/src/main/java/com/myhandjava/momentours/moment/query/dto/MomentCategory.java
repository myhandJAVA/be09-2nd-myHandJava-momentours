package com.myhandjava.momentours.moment.query.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MomentCategory {

    RESTAURANT("맛집"),
    CAFE("카페"),
    TRIP("여행"),
    ACTIVITY("체험"),
    WALK("산책");

    private final String koreanName;

    MomentCategory(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    @JsonCreator
    public static MomentCategory fromKoreanName(String koreanName) {
        for (MomentCategory category : MomentCategory.values()) {
            if (category.getKoreanName().equals(koreanName)) {
                return category;
            }
        }
        throw new IllegalArgumentException(koreanName + " 은(는) 존재하지 않는 카테고리입니다.");
    }



}
