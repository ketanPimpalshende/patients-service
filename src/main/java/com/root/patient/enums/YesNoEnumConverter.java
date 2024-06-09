package com.root.patient.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YesNoEnumConverter implements AttributeConverter<YesNoEnum, String> {

	@Override
	public String convertToDatabaseColumn(YesNoEnum attribute) {
		return attribute != null ? attribute.getValue() : null;
	}

	@Override
	public YesNoEnum convertToEntityAttribute(String dbData) {
		return dbData != null ? YesNoEnum.fromValue(dbData) : null;
	}
}
