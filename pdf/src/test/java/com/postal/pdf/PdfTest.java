package com.postal.pdf;

import com.jayway.jsonpath.internal.filter.LogicalOperator;
import com.mail.global.clients.authorities.Role;
import com.mail.global.clients.main.Client;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.clients.online.ClientOAuth2DTO;
import com.mail.global.clients.online.CommonClientDTO;
import com.mail.global.dto.Envelope;
import com.mail.pdf.PdfConstructor;
import com.mail.pdf.requests.DatabaseRequests;
import com.mail.pdf.service.PdfService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PdfConstructor.class})
public class PdfTest {
    @MockBean
    private DatabaseRequests requests;
    @Autowired
    private PdfService service;

    List<Client> allClients = List.of(
            Client.builder().email("rmail1").password("frgr=").appPassword("efrgtrrt").build(),
            Client.builder().email("email2").password("gtr==").appPassword("gtrgrt").build()
    );
    List<ClientDTO> online = List.of(
            new ClientOAuth2DTO("email1",null,"",""),
            new ClientOAuth2DTO("email2",null,"","")
    );
    ClientDTO admin = new ClientOAuth2DTO("email2",List.of(Role.ADMIN),"","");

    @Test
    public void adminStatTest(){
        when(requests.clients()).thenReturn(allClients);
        when(requests.online()).thenReturn(online);
        var file = service.adminStatistics(admin);
        Assertions.assertNotEquals(file,null);
    }

    @Test
    public void envelopeTest(){
        Envelope envelope = Envelope.builder().bodyText("<h1>Test</h1><p>Hello World</p>").build();
        service.readEnvelope(envelope);
    }
}
