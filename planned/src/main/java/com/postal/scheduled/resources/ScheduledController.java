package com.postal.scheduled.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.ScheduledMail;
import com.postal.scheduled.entity.ScheduledPost;
import com.postal.scheduled.repository.ScheduledRepository;
import com.postal.scheduled.service.ScheduledService;
import com.postal.scheduled.utils.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;


@RestController
public class ScheduledController {
    private final Logger log = LoggerFactory.getLogger(ScheduledController.class);
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ScheduledRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduledService scheduledService;
    private final Logger logger = LoggerFactory.getLogger(ScheduledController.class);

    @Autowired
    private Property property;

    @PostMapping("/put")
    public ResponseEntity<?> put(@RequestBody ScheduledMail post) {
        try {
            ScheduledPost scheduledPost = objectMapper
                    .readValue(objectMapper
                                    .writeValueAsBytes(post),
                            ScheduledPost.class);
            repository.save(scheduledPost);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            logger.warn("ScheduledController --- "+e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
    @Scheduled(fixedDelay = 20000)
    public void sendMailWithFixedDelay() throws IOException {
        ClientDTO[] clients = restTemplate.getForObject(property.getOnlineUsersUrl(), ClientDTO[].class);
        var result =  scheduledService.getPreparedPosts(clients);
        restTemplate.postForObject(property.getSendPostUrl(),result,String.class);
    }
}
