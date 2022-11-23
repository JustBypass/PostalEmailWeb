package com.logio.test;

import com.mail.logio.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Log.class)
@ActiveProfiles("test")
public class LogIOTest {

    @LocalServerPort
    int randomServerPort;
    private static TestRestTemplate testRestTemplate;

    private final String SERVICE_URT = "http://localhost:";

    @BeforeAll
    public static void init(){
        // @Autowired
        testRestTemplate = new TestRestTemplate();
    }
}
