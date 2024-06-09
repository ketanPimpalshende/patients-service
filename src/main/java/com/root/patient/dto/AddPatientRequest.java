package com.root.patient.dto;

import static com.root.patient.constants.ErrorMessageConstants.FIRST_NAME_CANNOT_BE_BLANK;
import static com.root.patient.constants.ErrorMessageConstants.LAST_NAME_CANNOT_BE_BLANK;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddPatientRequest {
    @NotBlank(message = FIRST_NAME_CANNOT_BE_BLANK)
    private String firstName;

    @NotBlank(message = LAST_NAME_CANNOT_BE_BLANK)
    private String lastName;

    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String postalcode;
    private String country;
    private String gender;
    private LocalDate date;
    private String insuranceProvider;
    private String insurancePolicyNumber;

    @Valid
    private EmergencyContactDTO emergencyContact;

}

