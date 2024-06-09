package com.root.patient.service;

import java.util.UUID;

import com.root.patient.dto.EmergencyContactRequest;
import com.root.patient.dto.GetEmergencyContactDTO;

public interface EmergencyContactService {

	public GetEmergencyContactDTO getEmergencyContactDetails(UUID logId, UUID patientNumber);
	
	public void updateEmergencyContactForPatient(UUID patientNumber, EmergencyContactRequest emergencyContactRequest);
	
}
