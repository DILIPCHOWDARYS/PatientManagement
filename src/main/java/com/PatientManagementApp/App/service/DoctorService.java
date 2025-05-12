package com.PatientManagementApp.App.service;

import com.PatientManagementApp.App.model.Doctor;
import com.PatientManagementApp.App.repository.DoctorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getById(String id) {
        return doctorRepository.findById(id);
    }

    // ✅ Seed doctors on startup
    @PostConstruct
    public void initDoctors() {
        if (doctorRepository.count() == 0) {
            List<Doctor> doctors = List.of(
                    Doctor.builder()
                            .id("1A1")
                            .name("Dr. Ram Kumar")
                            .email("ram.kumar@hospital.com")
                            .specialization("Cardiology")
                            .contactNumber("9876543210")
                            .verified(true)
                            .build(),

                    Doctor.builder()
                            .id("1A2")
                            .name("Dr. Priya Verma")
                            .email("priya.verma@hospital.com")
                            .specialization("Dermatology")
                            .contactNumber("9123456780")
                            .verified(true)
                            .build(),

                    Doctor.builder()
                            .id("2B1")
                            .name("Dr. Alok Sinha")
                            .email("alok.sinha@hospital.com")
                            .specialization("General Surgery")
                            .contactNumber("9112233445")
                            .verified(true)
                            .build(),

                    Doctor.builder()
                            .id("2B2")
                            .name("Dr. Meena Rathi")
                            .email("meena.rathi@hospital.com")
                            .specialization("Neurology")
                            .contactNumber("8899776655")
                            .verified(true)
                            .build(),

                    Doctor.builder()
                            .id("3C1")
                            .name("Dr. Faizan Ahmed")
                            .email("faizan.ahmed@hospital.com")
                            .specialization("Urology")
                            .contactNumber("9090909090")
                            .verified(true)
                            .build(),

                    Doctor.builder()
                            .id("3C2")
                            .name("Dr. Swetha Rao")
                            .email("swetha.rao@hospital.com")
                            .specialization("Pediatrics")
                            .contactNumber("9001122334")
                            .verified(true)
                            .build()
            );

            doctorRepository.saveAll(doctors);
            System.out.println("✅ Default doctors added to database.");
        }
    }
}
