package com.mail.control.utills;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Property {
    @Value("${fill-cache-url}")
    private String onlineUsersUrl;

    @Value("${delete-cached}")
    private String deleteCachedMessages;
}
