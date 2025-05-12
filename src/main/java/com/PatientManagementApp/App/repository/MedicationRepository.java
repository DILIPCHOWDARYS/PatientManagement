package com.PatientManagementApp.App.repository;

import com.PatientManagementApp.App.model.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MedicationRepository extends MongoRepository<Medication, String> {


    List<Medication> findByPatientNameIgnoreCase(String patientName);
}
