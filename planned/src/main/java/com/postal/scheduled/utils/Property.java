package com.postal.scheduled.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Property {
    @Value("${resources.online-users-url}")
    private String onlineUsersUrl;
    @Value("${resources.send}")
    private String sendPostUrl;
}
