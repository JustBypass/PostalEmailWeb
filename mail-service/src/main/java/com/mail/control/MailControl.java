package com.mail.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailControl {
    public static void main(String[] args) {
        SpringApplication.run(MailControl.class);
    }
}
