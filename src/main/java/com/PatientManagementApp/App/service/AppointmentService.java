package com.PatientManagementApp.App.service;

import com.PatientManagementApp.App.model.Appointment;
import com.PatientManagementApp.App.model.User;
import com.PatientManagementApp.App.repository.AppointmentRepository;
import com.PatientManagementApp.App.repository.DoctorRepository;
import com.PatientManagementApp.App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    // Used by both REST and MVC form submission
    public Appointment book(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // 🔧 Inject doctor name by patient ID
    public List<Appointment> getByPatientId(String patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        appointments.forEach(appt -> {
            doctorRepository.findById(appt.getDoctorId()).ifPresent(doctor -> {
                appt.setDoctorName(doctor.getName()); // ✅ Set doctorName for display
            });
        });
        return appointments;
    }

    // 🔧 Optional: if you want to support doctorName in this list too
    public List<Appointment> getByDoctorId(String doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        appointments.forEach(appt -> {
            doctorRepository.findById(appt.getDoctorId()).ifPresent(doctor -> {
                appt.setDoctorName(doctor.getName());
            });
        });
        return appointments;
    }

    public Optional<Appointment> getById(String id) {
        return appointmentRepository.findById(id);
    }

    public Appointment update(Appointment appointment) {
        if (appointment == null || appointment.getId() == null) {
            throw new IllegalArgumentException("Appointment or ID cannot be null");
        }
        return appointmentRepository.save(appointment);
    }

    // 🔧 Optional: useful if you show all appointments with names (e.g., admin view)
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        appointments.forEach(appt -> {
            doctorRepository.findById(appt.getDoctorId()).ifPresent(doctor -> {
                appt.setDoctorName(doctor.getName());
            });
        });
        return appointments;
    }

    public String getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
