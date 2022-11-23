package com.mail.control.resources;

import com.mail.control.utills.Property;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.methods.WithStreamMethods;
import com.mail.control.mail.MailAdminImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

@Component
public class ScheduledMailSendController {
    private final Logger log = LoggerFactory.getLogger(ScheduledMailSendController.class);

    @Autowired
    private Property property;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MailAdminImpl mailAdmin;
    @Scheduled(fixedDelay = 20000)
    public void fillCache()  {
        //HttpRequest request = HttpRequest.newBuilder(new URI(property.getOnlineUsersUrl())).GET().build();

       /* HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());*/


         log.info("Before 'restTemplate.getForObject'");
          var onlineUsers = restTemplate.getForObject(property.getOnlineUsersUrl(),ClientDTO[].class);
      //  ClientDTO[] onlineUsers =  response;
        log.info("This is online users: "+ Arrays.toString(onlineUsers));
        log.info("After 'restTemplate.getForObject'");
        mailAdmin.fillMessages(onlineUsers);
        log.info("After 'mailAdmin.fillMessages'");
    }
}
