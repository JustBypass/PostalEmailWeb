package com.mail.database.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.clients.online.ClientOAuth2DTO;
import com.mail.database.domain.Client;
import com.mail.database.domain.UserDTO;
import com.mail.database.repository.OnlineUsersRepository;
import com.mail.database.repository.UsersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("users")
public class UsersOperations {
    @Autowired
    private UsersRepository repository;

    private final Logger logger = LoggerFactory.getLogger(UsersOperations.class);
    @Autowired
    public OnlineUsersRepository onlineUsersRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Client client){
        if(client == null){
            throw new NullPointerException("Client object is null");
        }
        return null;
    }

    @PostMapping("/update/{email}")
    public Client update(@RequestBody Client client){
        return null;
    }

    @GetMapping("/delete/{email}")
    @PreAuthorize("hasAnyRole('ADMIN_ROLE','MODERATOR_ROLE')")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String email) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        executorService.submit(
                ()->repository.deleteById(email)
        );
        executorService.submit(
                ()->onlineUsersRepository.deleteById(email)
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/all")
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public ResponseEntity<?> deleteAll(){
        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        executorService.submit(
                ()->repository.deleteAll()
        );
        executorService.submit(
                ()->onlineUsersRepository.deleteAll()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{email}")
    public ClientDTO get(@PathVariable String email){
        return null;
    }

    @GetMapping("/online")
    public ResponseEntity<?> online() throws IOException {
        UserDTO[] uu = onlineUsersRepository.findAll().toArray(UserDTO[]::new);
        var src = new ObjectMapper()
                .writeValueAsBytes(uu);
        ClientDTO[] clients = new ObjectMapper()
                .readValue(src, ClientDTO[].class);

        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/me/delete")
    @Transactional
    public ResponseEntity<?> deleteMe(@AuthenticationPrincipal ClientDTO clientDTO){
        String email = clientDTO.getEmail();
        repository.deleteById(email);
        onlineUsersRepository.deleteById(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/changePassword")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal ClientDTO clientDTO,
                                            @RequestBody String newPassword){
        if(newPassword == null){
            throw new NullPointerException("New password id null");
        }

        String email = clientDTO.getEmail();

        if(clientDTO instanceof ClientOAuth2DTO){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        Client client = repository.getOne(email);
        client.setPassword(newPassword);
        return ResponseEntity.ok().body(repository.save(client));
    }

    @PostMapping("/online/add")
    public ResponseEntity<?> addOnlineUser(@RequestBody ClientDTO clientDTO) throws IOException {
        UserDTO user = new ObjectMapper().readValue(new ObjectMapper().writeValueAsBytes(clientDTO),UserDTO.class);

        UserDTO saved =  onlineUsersRepository.save(user);

        return ResponseEntity
                .ok()
                .body(new ObjectMapper()
                        .readValue(new ObjectMapper()
                                .writeValueAsBytes(saved), ClientDTO.class));
    }
}
