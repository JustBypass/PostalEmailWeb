package com.mail.cache.database.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mail.cache.CacheMessages;
import org.mail.cache.repository.CachedMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = CacheMessages.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControllersTest {

    @LocalServerPort
    int randomServerPort;
    private static TestRestTemplate testRestTemplate;

    private final String SERVICE_URT = "http://localhost:";

    @BeforeAll
    public static void init(){
      // @Autowired
       testRestTemplate = new TestRestTemplate();
    }

    @Autowired
    private CachedMessagesRepository repository;

    @Test
    public void doTest(){
        System.out.println();
    }
}
