package com.root.patient.util;

import java.util.UUID;

import com.root.patient.dto.AddPatientRequest;
import com.root.patient.enums.YesNoEnum;
import com.root.patient.model.EmergencyContact;
import com.root.patient.model.Patient;

public class CommonUtil {

	public static Patient buildPatientRequest(AddPatientRequest addPatientRequest) {
	    EmergencyContact emergencyContact = EmergencyContact.builder()
	            .emergencyName(addPatientRequest.getEmergencyContact().getEmergencyName())
	            .emergencyContactNumber(addPatientRequest.getEmergencyContact().getEmergencyPhoneNumber())
	            .build();

	    return Patient.builder()
	            .firstName(addPatientRequest.getFirstName())
	            .lastName(addPatientRequest.getLastName())
	            .email(addPatientRequest.getEmail())
	            .phoneNumber(addPatientRequest.getPhoneNumber())
	            .address(addPatientRequest.getAddress())
	            .city(addPatientRequest.getCity())
	            .state(addPatientRequest.getState())
	            .postalcode(addPatientRequest.getPostalcode())
	            .country(addPatientRequest.getCountry())
	            .gender(addPatientRequest.getGender())
	            .patientNumber(UUID.randomUUID())
	            .date(addPatientRequest.getDate())
	            .insuranceProvider(addPatientRequest.getInsuranceProvider())
	            .insurancePolicyNumber(addPatientRequest.getInsurancePolicyNumber())
	            .emergencyContact(emergencyContact)
	            .disabled(YesNoEnum.N)
	            .build();
	}

}
