package com.mail.global.clients.main;

import com.mail.global.clients.authorities.Role;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Client {
    private String email;
    private String password; // hash
    private String appPassword;
    private List<Role> roles = new ArrayList<>();
    private byte[] img;
}
