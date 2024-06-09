package com.root.patient.service;

import java.util.Optional;
import java.util.UUID;

import com.root.patient.dto.AddPatientRequest;
import com.root.patient.model.Patient;

public interface PatientService {

	public UUID addPatient(UUID logId, AddPatientRequest addPatientRequest);

	public Optional<Patient> getPatientDetailsById(UUID logId, UUID patientId);

	public void disablePatient(UUID logId, UUID patientNumber);

	public void enablePatient(UUID logId, UUID patientNumber);
}
