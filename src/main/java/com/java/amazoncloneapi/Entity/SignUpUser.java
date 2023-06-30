package com.java.amazoncloneapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SignUpUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordAgain;
    @JsonIgnore
    private String otp;

}
