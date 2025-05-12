package com.PatientManagementApp.App.controller;

import com.PatientManagementApp.App.model.Appointment;
import com.PatientManagementApp.App.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // ✅ Show booking form
    @GetMapping
    public String showBookingForm() {
        return "appointments"; // appointments.html
    }

    @GetMapping("/view")
    public String showAppointmentsPage(Model model, Principal principal) {
        String username = principal.getName(); // logged-in user's username
        String userId = appointmentService.getUserIdByUsername(username); // get their MongoDB _id
        List<Appointment> appointments = appointmentService.getByPatientId(userId); // fetch only their appointments
        model.addAttribute("appointments", appointments);
        return "view-appointments";
    }


    @PostMapping("/api")
    public String bookAppointment(Appointment appointment, Principal principal) {
        String username = principal.getName(); // ✅ logged-in user's username
        String userId = appointmentService.getUserIdByUsername(username); // ✅ get Mongo ID
        appointment.setPatientId(userId); // ✅ store it
        appointmentService.book(appointment);
        return "redirect:/appointments/view";
    }



    // ✅ API: Appointments by patient
    @GetMapping("/api/patient/{id}")
    @ResponseBody
    public List<Appointment> getAppointmentsByPatient(@PathVariable String id) {
        return appointmentService.getByPatientId(id);
    }

    // ✅ API: Appointments by doctor
    @GetMapping("/api/doctor/{id}")
    @ResponseBody
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String id) {
        return appointmentService.getByDoctorId(id);
    }
}
