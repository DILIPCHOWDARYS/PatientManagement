package com.PatientManagementApp.App.controller;

import com.PatientManagementApp.App.model.Medication;
import com.PatientManagementApp.App.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;


    @GetMapping("/medications")
    public String showMedicationsPage() {
        return "medications";
    }


    @PostMapping("/api/medications")
    @ResponseBody
    public Medication prescribe(@RequestBody Medication medication) {
        return medicationService.prescribe(medication);
    }


    @PostMapping("/api/medications/bulk")
    @ResponseBody
    public List<Medication> prescribeMultiple(@RequestBody List<Medication> medications) {
        try {
            System.out.println("✅ Received medications: " + medications);
            return medicationService.prescribeAll(medications);
        } catch (Exception e) {
            System.err.println("❌ Error saving medications: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @GetMapping("/api/medications/patient-name/{name}")
    @ResponseBody
    public List<Medication> getByPatientName(@PathVariable String name) {
        return medicationService.getByPatientName(name);
    }
}
