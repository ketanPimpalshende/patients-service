package com.root.patient.enums;

public enum YesNoEnum {
	Y("Y"), 
	N("N");

	private final String value;

	YesNoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static YesNoEnum fromValue(String value) {
		for (YesNoEnum yesNoEnum : YesNoEnum.values()) {
			if (yesNoEnum.value.equals(value)) {
				return yesNoEnum;
			}
		}
		throw new IllegalArgumentException("Invalid value: " + value);
	}
}
