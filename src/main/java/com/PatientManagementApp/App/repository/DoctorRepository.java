package com.PatientManagementApp.App.repository;

import com.PatientManagementApp.App.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
}
