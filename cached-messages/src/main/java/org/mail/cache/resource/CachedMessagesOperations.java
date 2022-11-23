package org.mail.cache.resource;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.MarkMailDTO;
import com.mail.global.search.mail.EnvelopeOperationsInfoDTO;
import org.mail.cache.service.CachedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("operations")
public class CachedMessagesOperations {
    @Autowired
    private CachedService service;

    @PostMapping(value="/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody MarkMailDTO envelopes){
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<?> getCountOfTopicsEmails(@AuthenticationPrincipal ClientDTO clientDTO) {
        try {
            return ResponseEntity
                    .ok()
                    .build();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
