package com.PatientManagementApp.App.repository;

import com.PatientManagementApp.App.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
