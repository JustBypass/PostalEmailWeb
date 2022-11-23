package com.mail.control.resources;

import com.mail.control.mail.MailAdminImpl;
import com.mail.global.dto.Post;
import com.mail.global.clients.online.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("microservice")
public class SubmittedServiceOperationsController {

    @Autowired
    private MailAdminImpl mailAdmin;
    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody List<Map.Entry<ClientDTO, List<Post>>> posts){
        mailAdmin.sendAll(posts);
        return ResponseEntity.ok().build();
    }
}
