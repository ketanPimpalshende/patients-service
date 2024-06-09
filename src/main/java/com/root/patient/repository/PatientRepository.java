package com.root.patient.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.root.patient.enums.YesNoEnum;
import com.root.patient.model.EmergencyContact;
import com.root.patient.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	Optional<Patient> findByPatientNumber(UUID patientNumber);

	Patient findByPatientNumberAndDisabled(UUID patientNumber, YesNoEnum y);

	Optional<Patient> findByPhoneNumber(String phoneNumber);

	@Query(value = "SELECT ec.* FROM emergency_contact ec " + "JOIN patient p ON ec.id = p.emergency_contact_id "
			+ "WHERE p.patient_number = :patientNumber", nativeQuery = true)
	EmergencyContact findEmergencyContactByPatientNumber(@Param("patientNumber") UUID patientNumber);

}
