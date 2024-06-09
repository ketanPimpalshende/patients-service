package com.root.patient.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.root.patient.constants.ErrorMessageConstants;
import com.root.patient.dto.EmergencyContactRequest;
import com.root.patient.dto.GetEmergencyContactDTO;
import com.root.patient.exception.ExceptionCodes;
import com.root.patient.exception.PlatformException;
import com.root.patient.model.EmergencyContact;
import com.root.patient.model.Patient;
import com.root.patient.repository.EmergencyContactRepository;
import com.root.patient.repository.PatientRepository;
import com.root.patient.service.EmergencyContactService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmergencyContactServiceImpl implements EmergencyContactService {
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private EmergencyContactRepository emergencyContactRepository;
	
	
	@Transactional
	@Override
	public GetEmergencyContactDTO getEmergencyContactDetails(UUID logId, UUID patientNumber) {
		log.info("logId: {} :: Fetching emergency contact details for Patient number: {}", logId, patientNumber);
		try {
			Patient patient = patientRepository.findByPatientNumber(patientNumber)
					.orElseThrow(() -> new PlatformException(ExceptionCodes.PATIENT_NOT_FOUND.getErrorCode(),
							ErrorMessageConstants.PATIENT_NOT_FOUND));

			EmergencyContact emergencyContact = patient.getEmergencyContact();
			if (emergencyContact == null) {
				log.warn("logId: {} :: Emergency contact details not found for Patient number: {}", logId,
						patientNumber);
				throw new PlatformException(ExceptionCodes.EMERGENCY_CONTACT_NOT_FOUND.getErrorCode(),
						ErrorMessageConstants.EMERGENCY_CONTACT_NOT_FOUND);
			}

			log.info("logId: {} :: Emergency contact details found for Patient number: {}", logId, patientNumber);
			GetEmergencyContactDTO getEmergencyContactDTO = new GetEmergencyContactDTO();
			getEmergencyContactDTO.setPatientNumber(patientNumber.toString());
			getEmergencyContactDTO.setEmergencyName(emergencyContact.getEmergencyName());
			getEmergencyContactDTO.setEmergencyContactNumber(emergencyContact.getEmergencyContactNumber());
			return getEmergencyContactDTO;
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error(
					"logId: {} :: An error occurred while fetching emergency contact details for Patient number: {}. Error Message : {}",
					logId, patientNumber, e.getMessage());
			throw new PlatformException(ExceptionCodes.EMERGENCY_CONTACT_FETCH_FAILED.getErrorCode(),
					ErrorMessageConstants.EMERGENCY_CONTACT_FETCH_FAILED);
		}
	}

	@Override
	@Transactional
	public void updateEmergencyContactForPatient(UUID patientNumber, EmergencyContactRequest emergencyContactRequest) {
		log.info("Attempting to update emergency contact details for patient with patient number: {}", patientNumber);
		try {
			Patient patient = patientRepository.findByPatientNumber(patientNumber)
					.orElseThrow(() -> new PlatformException(ExceptionCodes.PATIENT_NOT_FOUND.getErrorCode(),
							ErrorMessageConstants.PATIENT_NOT_FOUND));
			
			EmergencyContact emergencyContact = new EmergencyContact();
			emergencyContact.setEmergencyName(emergencyContactRequest.getEmergencyName());
			emergencyContact.setEmergencyContactNumber(emergencyContactRequest.getEmergencyPhoneNumber());
			emergencyContactRepository.save(emergencyContact);

			patient.setEmergencyContact(emergencyContact);
			patientRepository.save(patient);

			log.info("Emergency contact details updated successfully for patient with patient number: {}", patientNumber);
		} catch (PlatformException e) {
			log.error("Patient not found with patient number: {}", patientNumber);
			throw e;
		} catch (Exception e) {
			log.error(
					"An error occurred while updated emergency contact details for patient with patient number: {} - Error Message : {}",
					patientNumber, e.getMessage());
			throw new RuntimeException("Failed to update emergency contact details");
		}
	}

}
