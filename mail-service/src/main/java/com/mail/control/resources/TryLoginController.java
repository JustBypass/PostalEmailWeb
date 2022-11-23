package com.mail.control.resources;

import com.mail.global.clients.online.ClientDTO;
import com.mail.control.utills.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
class TryLoginController {
    @Autowired
    private MailUtils mailUtils;
    private final Logger logger = LoggerFactory.getLogger(TryLoginController.class);
    @PostMapping
    public ResponseEntity<?> login(@RequestBody ClientDTO clientDTO) {
        try {
            mailUtils.login(clientDTO);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
