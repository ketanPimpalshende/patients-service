package com.root.patient.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCodes {

	// Platform Error Codes - PE4XX
	PATIENT_CREATION_FAILED("PE400", BAD_REQUEST), 
	PATIENT_ALREADY_EXISTS("PE401", BAD_REQUEST),
	PATIENT_NOT_FOUND("PE402", BAD_REQUEST), 
	PATIENT_DISABLING_FAILED("PE403", BAD_REQUEST), 
	PATIENT_DETAILS_FETCH_FAILED("PE404", BAD_REQUEST), 
	PATIENT_ALREADY_DISABLED("PE405", BAD_REQUEST),
	PATIENT_ALREADY_ENABLED("PE406", BAD_REQUEST), 
	PATIENT_ENABLING_FAILED("PE407", BAD_REQUEST), 
	PATIENT_DISABLED("PE408", BAD_REQUEST), 
	EMERGENCY_CONTACT_NOT_FOUND("PE409", BAD_REQUEST), 
	EMERGENCY_CONTACT_FETCH_FAILED("PE410", BAD_REQUEST);

	private final String errorCode;
	private final HttpStatus status;

}
