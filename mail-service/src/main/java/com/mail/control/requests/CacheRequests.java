package com.mail.control.requests;

import com.mail.control.mail.MailAdminImpl;
import com.mail.control.utills.Property;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.Envelope;
import com.mail.global.dto.MarkMailDTO;
import com.mail.global.search.mail.EnvelopeOperationsInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class CacheRequests {
    @Autowired
    private Property property;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(MailAdminImpl.class);

    public void fillCacheInFolder(List<Envelope> envelopeList,String folder,String email){
        var resp = restTemplate.postForObject(String.format("%s/%s/%s",property.getOnlineUsersUrl(),folder,email),
                envelopeList, String.class);
        logger.info("MailAdminImpl --- " + resp);
    }
    public void setSeenStatus(ClientDTO clientDTO,MarkMailDTO envelopes){

    }

    public void deleteEmailFromCache(MarkMailDTO envelopes,
                                            ClientDTO clientDTO){

    }
}
