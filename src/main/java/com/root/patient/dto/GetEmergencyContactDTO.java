package com.root.patient.dto;

import lombok.Data;

@Data
public class GetEmergencyContactDTO {
    private String patientNumber;
    private String emergencyName;
    private Long emergencyContactNumber;
}

