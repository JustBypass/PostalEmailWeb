package com.mail.control.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.dto.EnvelopeSpamFilterDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class SpamRequests {
    public boolean checkIfMessageIsSpam(EnvelopeSpamFilterDTO envelopeSpamFilterDTO) throws IOException, InterruptedException, URISyntaxException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI("http://localhost:9097/filter"))
                .POST(HttpRequest
                                .BodyPublishers
                                .ofByteArray(envelopeSpamFilterDTO
                                        .toString()
                                        .getBytes())
                         ).build();
        var response = client.send(request, HttpResponse
                .BodyHandlers.ofInputStream()
        );
        System.out.println(response.body());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper
                .readValue(response.body(),Boolean.class);
    }
}