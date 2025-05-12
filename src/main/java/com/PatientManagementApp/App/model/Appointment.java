package com.PatientManagementApp.App.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment {
    @Id
    private String id;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String reason;
    private String patientName;

    @Transient
    private String doctorName;
}
