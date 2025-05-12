package com.PatientManagementApp.App.service;

import com.PatientManagementApp.App.model.Medication;
import com.PatientManagementApp.App.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public Medication prescribe(Medication medication) {
        return medicationRepository.save(medication);
    }

    public List<Medication> prescribeAll(List<Medication> medications) {
        return medicationRepository.saveAll(medications);
    }

    public List<Medication> getByPatientName(String name) {
        return medicationRepository.findByPatientNameIgnoreCase(name);
    }
}
