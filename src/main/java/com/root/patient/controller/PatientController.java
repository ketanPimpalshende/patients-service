package com.root.patient.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.root.patient.constants.GenericConstants;
import com.root.patient.dto.AddPatientRequest;
import com.root.patient.model.Patient;
import com.root.patient.service.PatientService;

import jakarta.validation.Valid;

@RequestMapping("/patient")
@RestController
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping(value = "/add-patient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addPatient(@RequestHeader(GenericConstants.LOG_ID) UUID logId,
			@Valid @RequestBody AddPatientRequest addPatientRequest) {
		UUID patientNumber = patientService.addPatient(logId, addPatientRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Patient added successfully. Patient number : " + patientNumber);
	}

	@GetMapping(value = "/get-patient/{patientNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> getPatientDetailsById(@RequestHeader(GenericConstants.LOG_ID) UUID logId,
			@PathVariable("patientNumber") UUID patientNumber) {
		Optional<Patient> patientOptional = patientService.getPatientDetailsById(logId, patientNumber);
		return patientOptional.map(patient -> ResponseEntity.ok().body(patient))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(value = "enable-patient/{patientNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> enablePatient(@RequestHeader(GenericConstants.LOG_ID) UUID logId,
			@PathVariable("patientNumber") UUID patientNumber) {
		patientService.enablePatient(logId, patientNumber);
		return ResponseEntity.ok("Patient with patient number : " + patientNumber + " enabled successfully");
	}

	@PostMapping(value = "disable-patient/{patientNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletePatient(@RequestHeader(GenericConstants.LOG_ID) UUID logId,
			@PathVariable("patientNumber") UUID patientNumber) {
		patientService.disablePatient(logId, patientNumber);
		return ResponseEntity.ok("Patient with patient number : " + patientNumber + " disabled successfully");
	}

}
