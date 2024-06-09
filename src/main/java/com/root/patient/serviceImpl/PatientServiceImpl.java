package com.root.patient.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.root.patient.constants.ErrorMessageConstants;
import com.root.patient.dto.AddPatientRequest;
import com.root.patient.enums.YesNoEnum;
import com.root.patient.exception.ExceptionCodes;
import com.root.patient.exception.PlatformException;
import com.root.patient.model.Patient;
import com.root.patient.repository.PatientRepository;
import com.root.patient.service.PatientService;
import com.root.patient.util.CommonUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	@Transactional
	public UUID addPatient(UUID logId, AddPatientRequest addPatientRequest) {
		log.info("logId: {} :: Attempting to create patient", logId);
		try {
			Optional<Patient> existingPatient = patientRepository.findByPhoneNumber(addPatientRequest.getPhoneNumber());

			if (existingPatient.isPresent()) {
				throw new PlatformException(ExceptionCodes.PATIENT_ALREADY_EXISTS.getErrorCode(),
						ErrorMessageConstants.PATIENT_ALREADY_EXISTS);
			}

			Patient patient = CommonUtil.buildPatientRequest(addPatientRequest);
			patient.setPatientNumber(UUID.randomUUID()); // Generate patientNumber
			patientRepository.save(patient);
			log.info("logId: {} :: Patient created successfully. Patient number : {}", logId, patient.getId());
			return patient.getPatientNumber();
		} catch (PlatformException e) {
			throw e; // Re-throw PlatformException to ensure it's caught by the global exception
						// handler
		} catch (Exception e) {
			log.error("logId: {} :: Failed to create patient. Error: {}", logId, e.getMessage(), e);
			throw new PlatformException(ExceptionCodes.PATIENT_CREATION_FAILED.getErrorCode(),
					ErrorMessageConstants.PATIENT_CREATION_FAILED);
		}
	}

	@Override
	public Optional<Patient> getPatientDetailsById(UUID logId, UUID patientNumber) {
		log.info("logId: {} :: Fetching patient details for Patient number : {}", logId, patientNumber);
		try {
			Optional<Patient> optionalPatient = patientRepository.findByPatientNumber(patientNumber);
			if (optionalPatient.isPresent()) {
				Patient patient = optionalPatient.get();
				if (patient.getDisabled() == YesNoEnum.Y) {
					log.warn("logId: {} :: Patient with Patient number {} is disabled", logId, patientNumber);
					throw new PlatformException(ExceptionCodes.PATIENT_DISABLED.getErrorCode(),
							ErrorMessageConstants.PATIENT_DISABLED);
				}
				log.info("logId: {} :: Patient details found for Patient number : {}", logId, patientNumber);
				return optionalPatient;
			} else {
				log.warn("logId: {} :: Patient not found for Patient number : {}", logId, patientNumber);
				throw new PlatformException(ExceptionCodes.PATIENT_NOT_FOUND.getErrorCode(),
						ErrorMessageConstants.PATIENT_NOT_FOUND);
			}
		} catch (PlatformException e) {
			throw e; // Re-throw PlatformException to ensure it's caught by the global exception
						// handler
		} catch (Exception e) {
			log.error("logId: {} :: An error occurred while fetching patient details for Patient number : {}", logId,
					patientNumber, e.getMessage());
			throw new PlatformException(ExceptionCodes.PATIENT_DETAILS_FETCH_FAILED.getErrorCode(),
					ErrorMessageConstants.PATIENT_DETAILS_FETCH_FAILED);
		}
	}

	@Transactional
	@Override
	public void disablePatient(UUID logId, UUID patientNumber) {
		log.info("logId: {} :: Disabling patient with patient number: {}", logId, patientNumber);
		try {
			Patient patient = patientRepository.findByPatientNumber(patientNumber)
					.orElseThrow(() -> new PlatformException(ExceptionCodes.PATIENT_NOT_FOUND.getErrorCode(),
							ErrorMessageConstants.PATIENT_NOT_FOUND));

			if (patient.getDisabled() == YesNoEnum.Y) {
				throw new PlatformException(ExceptionCodes.PATIENT_ALREADY_DISABLED.getErrorCode(),
						ErrorMessageConstants.PATIENT_ALREADY_DISABLED);
			}

			patient.setDisabled(YesNoEnum.Y); // Update only the disabled field
			patientRepository.save(patient);
			log.info("logId: {} :: Patient with patient number {} disabled successfully", logId, patientNumber);
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error("logId: {} :: An error occurred while disabling patient with patient number : {}", logId,
					patientNumber, e.getMessage());
			throw new PlatformException(ExceptionCodes.PATIENT_DISABLING_FAILED.getErrorCode(),
					ErrorMessageConstants.PATIENT_DISABLING_FAILED);
		}
	}

	@Transactional
	@Override
	public void enablePatient(UUID logId, UUID patientNumber) {
		log.info("logId: {} :: Enabling patient with patient number: {}", logId, patientNumber);
		try {
			Patient patient = patientRepository.findByPatientNumber(patientNumber)
					.orElseThrow(() -> new PlatformException(ExceptionCodes.PATIENT_NOT_FOUND.getErrorCode(),
							ErrorMessageConstants.PATIENT_NOT_FOUND));

			if (patient.getDisabled() == YesNoEnum.N) {
				throw new PlatformException(ExceptionCodes.PATIENT_ALREADY_ENABLED.getErrorCode(),
						ErrorMessageConstants.PATIENT_ALREADY_ENABLED);
			}

			patient.setDisabled(YesNoEnum.N); // Update only the disabled field
			patientRepository.save(patient);
			log.info("logId: {} :: Patient with patient number {} enabled successfully", logId, patientNumber);
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error("logId: {} :: An error occurred while enabling patient with patient number: {}", logId,
					patientNumber, e.getMessage());
			throw new PlatformException(ExceptionCodes.PATIENT_ENABLING_FAILED.getErrorCode(),
					ErrorMessageConstants.PATIENT_ENABLING_FAILED);
		}
	}
}
