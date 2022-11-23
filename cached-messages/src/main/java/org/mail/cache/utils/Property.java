package org.mail.cache.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Property {
    @Value("${resources.auth-url}")
    private String authUrl;

}
