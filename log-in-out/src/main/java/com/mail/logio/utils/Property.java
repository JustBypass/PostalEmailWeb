package com.mail.logio.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Property {
    @Value("database-login-url")
    private String databaseLogin;

    @Value("jwt")
    private String SECRET;

    @Value("mail-login-url")
    private String MAIL_LOGIN_URL;
}
