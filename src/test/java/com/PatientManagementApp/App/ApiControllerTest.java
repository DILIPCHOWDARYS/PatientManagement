package com.PatientManagementApp.App;

import com.PatientManagementApp.App.model.User;
import com.PatientManagementApp.App.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.PatientManagementApp.App.model.Appointment;
import com.PatientManagementApp.App.model.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        if (!userRepository.findByUsername("user").isPresent()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            userRepository.save(user);
        }
    }

    @Test
    @WithMockUser(username = "user")
    void testViewAppointments() throws Exception {
        mockMvc.perform(get("/appointments/view"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void testBookAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setDoctorId("doctorId");
        appointment.setDate("2025-05-15");
        appointment.setTime("10:00");
        appointment.setReason("General Checkup");

        mockMvc.perform(post("/appointments/api")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("doctorId", appointment.getDoctorId())
                        .param("date", appointment.getDate())
                        .param("time", appointment.getTime())
                        .param("reason", appointment.getReason()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUserRegistration() throws Exception {
        String username = "testuser123";

        // Cleanup if user already exists
        userRepository.findByUsername(username).ifPresent(userRepository::delete);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", username)
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    void testUserLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testViewDoctors() throws Exception {
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testViewMedications() throws Exception {
        mockMvc.perform(get("/medications"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testPrescribeMedication() throws Exception {
        Medication medication = new Medication();
        medication.setPatientName("John Doe");
        medication.setDosage("500mg");

        mockMvc.perform(post("/api/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medication)))
                .andExpect(status().isOk());
    }
}
