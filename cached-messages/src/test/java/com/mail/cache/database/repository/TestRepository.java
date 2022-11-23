package com.mail.cache.database.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mail.cache.CacheMessages;
import org.mail.cache.domain.Envelope;
import org.mail.cache.repository.CachedMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = CacheMessages.class)
@ActiveProfiles("test")
public class TestRepository {

    @Autowired
    private CachedMessagesRepository cachedMessagesRepository;

    private final String TEXT1 = "Java program to deserialize list of objects and verify list content";
    private final String TEXT2 = "Given below is an example Java program to save an arraylist of Employee objects. Employee class implement Serializable interface";


    private final static String HOST = "email";
    @BeforeEach
    public void fill_table(){
        List<Envelope> envelopes = new ArrayList<>();

        envelopes.add( Envelope.builder()
                .bodyText(TEXT1)
                        .host(HOST)
                .build());
        envelopes.add(Envelope.builder()
                .bodyText(TEXT2)
                .host(HOST)
                .build());

        cachedMessagesRepository.saveAll(envelopes);
    }

    @AfterEach
    public void delete_all_entities_in_table(){


        cachedMessagesRepository.deleteAll();
    }

    @Test
    public void search_text()  {
        var saved = cachedMessagesRepository.findAll();
        Assertions.assertEquals((saved.size()) , 2);
    }
    @Test
    public void check_if_global_envelop_equals_to_local() throws IOException {
        var saved = cachedMessagesRepository.findAll();
        Envelope[] array = saved.toArray(Envelope[]::new);
        var src = new ObjectMapper()
                .writeValueAsBytes(array);
        com.mail.global.dto.Envelope[] globalEnvelopes =  new ObjectMapper()
                .readValue(src,com.mail.global.dto.Envelope[].class);
        Assertions.assertEquals(TEXT1, globalEnvelopes[0].getBodyText());
        Assertions.assertEquals(TEXT2, globalEnvelopes[1].getBodyText());
        Assertions.assertEquals(2, globalEnvelopes.length);
    }
}
