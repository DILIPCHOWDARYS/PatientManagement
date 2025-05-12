package com.PatientManagementApp.App.controller;

import com.PatientManagementApp.App.model.User;
import com.PatientManagementApp.App.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ Get all users
    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // You must create users.html
    }

    // ✅ Get user by username
    @GetMapping("/view/{username}")
    public String getUserByUsername(@PathVariable String username, Model model) {
        Optional<User> userOpt = Optional.ofNullable(userService.findByUsername(username));
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "user-details"; // You must create user-details.html
        } else {
            model.addAttribute("error", "User not found.");
            return "error"; // generic error.html
        }
    }

    // ✅ Delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return "redirect:/users/all";
    }

    // ✅ Edit user - show form
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable String id, Model model) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "edit-user"; // You must create edit-user.html
        } else {
            model.addAttribute("error", "User not found.");
            return "error";
        }
    }

    // ✅ Update user - submit form
    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/users/all";
    }
}
