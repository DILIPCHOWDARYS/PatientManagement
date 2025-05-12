package com.PatientManagementApp.App.service;

import com.PatientManagementApp.App.model.User;
import com.PatientManagementApp.App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findAllByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        } else if (users.size() > 1) {
            throw new UsernameNotFoundException("Multiple users found with username: " + username + ". Ensure uniqueness.");
        }
        User user = users.get(0);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }


}

