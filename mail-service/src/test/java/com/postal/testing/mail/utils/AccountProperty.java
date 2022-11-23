package com.postal.testing.mail.utils;

import com.mail.global.clients.online.CommonClientDTO;
import com.mail.global.clients.authorities.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
public class AccountProperty {
    @Value("${mail.account.email}")
    public String email;
    @Value("${mail.account.appPassword}")
    public  String appPassword;

    @Bean
    public CommonClientDTO commonClientDTO(){
        return new CommonClientDTO(email, List.of(Role.USER),appPassword);
    }
}
