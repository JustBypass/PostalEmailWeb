package com.mail.control.resources;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.MarkMailDTO;
import com.mail.global.search.mail.EnvelopeOperationsInfoDTO;
import com.mail.control.mail.MailAdminImpl;
import com.mail.control.requests.CacheRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("ops")
public class OperationsController {
    @Autowired
    private CacheRequests cacheRequests;
    @Autowired
    private MailAdminImpl mailAdmin;
    
    private final Logger logger = LoggerFactory.getLogger(MailAdminImpl.class);
    @PostMapping(value="/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> delete(@RequestBody MarkMailDTO envelopes,
                                    @AuthenticationPrincipal ClientDTO clientDTO){
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);
        executorService.submit(()->{
            mailAdmin.deleteMessages(envelopes,clientDTO);
        });
        executorService.submit(()->{
            cacheRequests.deleteEmailFromCache(envelopes,clientDTO);
        });

        /// todo deleteFromBasket
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toSpam")
    @Transactional
    public ResponseEntity<?> toSpam(@AuthenticationPrincipal ClientDTO clientDTO
            ,@RequestBody MarkMailDTO envelope){
        // to mail spam
        // to cache spam
        return null;
    }

    @PostMapping("/mark")
    @Transactional
    public ResponseEntity<?> mark(@AuthenticationPrincipal ClientDTO clientDTO,
                                  @RequestBody MarkMailDTO envelopes){
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);
        executorService.submit(()->{
            mailAdmin.markAsReadAll(clientDTO, envelopes);

        });
        executorService.submit(()->{
            cacheRequests.setSeenStatus(clientDTO,envelopes);
        });

        return ResponseEntity.ok().build();
    }
}
