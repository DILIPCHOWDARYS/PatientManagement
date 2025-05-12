package com.PatientManagementApp.App.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "doctors")
public class Doctor {
    @Id
    private String id;
    private String name;
    private String email;
    private String specialization;
    private String contactNumber;
    private boolean verified;
}

