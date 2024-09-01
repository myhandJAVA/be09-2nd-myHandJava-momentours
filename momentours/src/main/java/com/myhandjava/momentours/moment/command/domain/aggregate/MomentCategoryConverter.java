package com.myhandjava.momentours.moment.command.domain.aggregate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MomentCategoryConverter implements AttributeConverter<MomentCategory, String> {

    @Override
    public String convertToDatabaseColumn(MomentCategory momentCategory) {
        if (momentCategory == null) {
            return null;
        }
        return momentCategory.getKoreanName();
    }

    @Override
    public MomentCategory convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return MomentCategory.getMomentCategory(dbData);
    }
}
