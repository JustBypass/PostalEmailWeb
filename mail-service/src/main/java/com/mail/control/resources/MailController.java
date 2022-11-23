package com.mail.control.resources;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.Post;
import com.mail.control.domain.SendMailDTO;
import com.mail.control.domain.ServerMailPort;
import com.mail.control.mail.MailAdminImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailAdminImpl mailAdmin;

    @Autowired
    private ServerMailPort serverMailPort;

    @GetMapping("/send")
    @PreAuthorize("hasAuthority('SEND')")
    public ResponseEntity<?> send(@RequestBody Post post, @AuthenticationPrincipal ClientDTO clientDTO){
        if(post == null){
            throw new NullPointerException("Post is not presented");
        }
        try {
            mailAdmin.sendMail(SendMailDTO.builder().clientDTO(clientDTO).serverInfo(
                            serverMailPort
                            .getHostByEmail(clientDTO.getEmail()))
                    .post(post)
                    .build());
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
