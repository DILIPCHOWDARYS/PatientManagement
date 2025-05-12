package com.PatientManagementApp.App.controller;

import com.PatientManagementApp.App.model.Doctor;
import com.PatientManagementApp.App.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public Doctor saveDoctor(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable String id) {
        return doctorService.getById(id).orElse(null);
    }
}
