package com.mail.spamfilter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHORIZATION;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
       return  http.authorizeExchange(
                exchanges->exchanges.anyExchange().permitAll()/*.and().addFilterAt(retrieverFilter(),
                        AUTHORIZATION)*/
        ).build();
    }
}
