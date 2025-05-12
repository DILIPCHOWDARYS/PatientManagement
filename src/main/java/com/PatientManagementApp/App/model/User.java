package com.PatientManagementApp.App.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String email;

    // Setter
    // Getter
    @Setter
    @Getter
    private boolean enabled = true; // <-- Add this field

    private List<String> roles = new ArrayList<>();


}
