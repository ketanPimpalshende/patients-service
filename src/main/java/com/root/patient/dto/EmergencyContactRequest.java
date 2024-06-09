package com.root.patient.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmergencyContactRequest {

    @NotBlank(message = "Emergency contact name cannot be blank")
    private String emergencyName;

    @Nonnull
    private Long emergencyPhoneNumber;

}

