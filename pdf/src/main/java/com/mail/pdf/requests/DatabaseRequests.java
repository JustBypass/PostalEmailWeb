package com.mail.pdf.requests;

import com.mail.global.clients.main.Client;
import com.mail.global.clients.online.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DatabaseRequests {
    @Autowired
    private RestTemplate restTemplate;
    public List<ClientDTO> online(){
        return null;
    }
    public List<Client> clients(){
        return null;
    }

}
