package com.mail.database.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.methods.WithStreamMethods;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public WithStreamMethods withStreamMethods(){
        return new WithStreamMethods();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.authorizeRequests(
                req->req
                        .antMatchers("/users/online/**")
                        .permitAll()
                        .antMatchers("/login")
                        .permitAll()
                        .antMatchers("/registration")
                        .permitAll()
                        .anyRequest().permitAll()
        );
    }
}
