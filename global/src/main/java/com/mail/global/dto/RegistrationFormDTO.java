package com.mail.global.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class RegistrationFormDTO implements Serializable {
    private String email;
    private String appPassword;
    private String password;
    private String name;
    private String female;
    private String city;
    private LocalDate birthData;
    private byte[] image;
}
