package com.PatientManagementApp.App.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medications")
public class Medication {

    @Id
    private String id;

    private String patientName;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
}
