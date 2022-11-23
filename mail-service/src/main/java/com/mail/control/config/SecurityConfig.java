package com.mail.control.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.control.utills.Property;
import com.mail.global.methods.WithStreamMethods;
import com.global.filters.JwtCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public WithStreamMethods withStreamMethods(){
        return new WithStreamMethods();
    }

    @Autowired
    private Property property;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.authorizeRequests(
                exchanges->
                        exchanges
                                .antMatchers("/login").permitAll()
                                .anyRequest().authenticated()
        );
        http.addFilterBefore(new JwtCheckFilter(property.getOnlineUsersUrl()),UsernamePasswordAuthenticationFilter.class);
    }
}
