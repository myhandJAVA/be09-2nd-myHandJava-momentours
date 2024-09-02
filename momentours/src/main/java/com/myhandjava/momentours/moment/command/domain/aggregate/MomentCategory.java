package com.myhandjava.momentours.moment.command.domain.aggregate;

public enum MomentCategory {
    맛집("맛집"),
    카페("카페"),
    여행("여행"),
    체험("체험"),
    산책("산책");

    private final String koreanName;

    MomentCategory(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    /* 설명. DB로부터 Enum을 얻는 메소드 */
    public static MomentCategory getMomentCategory(String koreanName) {
        for (MomentCategory momentCategory : MomentCategory.values()) {
            if (momentCategory.getKoreanName().equals(koreanName)) {
                return momentCategory;
            }
        }
        throw new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다.");
    }
}
