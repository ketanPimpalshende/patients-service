package com.root.patient.constants;

public class ErrorMessageConstants {

    private ErrorMessageConstants() {
    }

    public static final String PATIENT_CREATION_FAILED = "Patient creation failed";
    
    //Validation constants
	public static final String FIRST_NAME_CANNOT_BE_BLANK = "First name can not be blank";
	public static final String LAST_NAME_CANNOT_BE_BLANK = "Last name can not be blank";
	public static final String PATIENT_ALREADY_EXISTS = "Patient Already exists";
	public static final String PATIENT_NOT_FOUND = "Patient not found";
	public static final String PATIENT_DETAILS_FETCH_FAILED = "FAILED :: Get patient details";
	
	public static final String PATIENT_ALREADY_DISABLED = "Patient is already disabled";
	public static final String PATIENT_DISABLING_FAILED = "Patient disabling failed";

	public static final String PATIENT_ALREADY_ENABLED = "Patient is already enabled";
	public static final String PATIENT_ENABLING_FAILED = "Patient enabling failed";

	public static final String PATIENT_DISABLED = "Patient is disbaled";
	
	public static final String EMERGENCY_CONTACT_NOT_FOUND = "Emergency contact not found";
	public static final String EMERGENCY_CONTACT_FETCH_FAILED = "FAILED :: get emergency contact details";

}
