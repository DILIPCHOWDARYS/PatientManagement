package com.PatientManagementApp.App.controller;

import com.PatientManagementApp.App.model.User;
import com.PatientManagementApp.App.service.DoctorService;
import com.PatientManagementApp.App.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final DoctorService doctorService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("user") User user) {
        System.out.println("Registering: " + user.getUsername());
        userService.saveUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    // ✅ Show Forgot Password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    // ✅ Show Doctors Page
    @GetMapping("/doctors")
    public String showDoctorsPage(Model model) {
        model.addAttribute("doctors", doctorService.getAll());
        return "doctors";
    }

    // ✅ Handle Forgot Password submission (by username)
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String username, Model model) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return "redirect:/reset-password?username=" + username;
        } else {
            model.addAttribute("message", "No user found with that username.");
            return "forgot-password";
        }
    }

    // ✅ Show Reset Password page
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "reset-password";
    }

    // ✅ Handle Reset Password (with confirm password)
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("username", username);
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password";
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("message", "User not found for username: " + username);
            return "forgot-password";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userService.saveUser(user);

        model.addAttribute("success", "Password reset successfully! Please log in.");
        return "login";
    }
}
