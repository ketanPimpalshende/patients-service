package com.root.patient.model;


import java.time.LocalDate;
import java.util.UUID;

import com.root.patient.enums.YesNoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String address;
	private String city;
	private String state;
	private String postalcode;
	private String country;
	private String gender;
	private UUID patientNumber;
	private LocalDate date;
	private String insuranceProvider;
	private String insurancePolicyNumber;
    private YesNoEnum disabled;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emergency_contact_id", referencedColumnName = "id")
    private EmergencyContact emergencyContact;
	
}
