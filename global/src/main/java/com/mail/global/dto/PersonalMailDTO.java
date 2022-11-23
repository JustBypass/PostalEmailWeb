package com.mail.global.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalMailDTO implements Serializable {
    private String folder;
    private long uuid;
    private String email;
    private String appPassword;
}
