package com.root.patient.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.root.patient.constants.GenericConstants;
import com.root.patient.dto.EmergencyContactRequest;
import com.root.patient.dto.GetEmergencyContactDTO;
import com.root.patient.service.EmergencyContactService;

import jakarta.validation.Valid;

@RequestMapping("/emergency")
@RestController
public class EmergencyContactController {

	@Autowired
	private EmergencyContactService emergencyContactService;

	@GetMapping(value = "/emergency-contact/{patientNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetEmergencyContactDTO> fetchEmergencyContact(
			@RequestHeader(GenericConstants.LOG_ID) UUID logId, @PathVariable("patientNumber") UUID patientNumber) {
		GetEmergencyContactDTO getEmergencyContact = emergencyContactService.getEmergencyContactDetails(logId,
				patientNumber);
		if (getEmergencyContact != null) {
			return ResponseEntity.ok(getEmergencyContact);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/emergency-contact/{patientNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmergencyContactForPatient(@PathVariable UUID patientNumber,
			@Valid @RequestBody EmergencyContactRequest emergencyContactRequest) {
		emergencyContactService.updateEmergencyContactForPatient(patientNumber, emergencyContactRequest);
		return ResponseEntity.ok("Emergency contact details updated successfully");
	}

}
