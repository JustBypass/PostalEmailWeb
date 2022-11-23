package com.mail.logio.resources;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.dto.LoginForm;
import com.mail.global.dto.RegistrationFormDTO;
import com.mail.global.clients.online.CommonClientDTO;
import com.mail.global.clients.authorities.Role;
import com.mail.logio.requests.Requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class LogIOController {
    @Autowired
    private Requests requests;

    private final Logger logger = LoggerFactory.getLogger(LogIOController.class);
    @PostMapping("/common")
    public ResponseEntity<?> login(@RequestBody LoginForm form, HttpServletResponse response) {
        try {
                ClientDTO clientDTO = requests.login(form);

                logger.info(clientDTO.toString());

                Map<String,String> tokens = new HashMap<>();
                String accessToken = "";
                String refreshToken = "";
                tokens.put("accessToken",accessToken);

                Cookie cookie = new Cookie("Postal-Cookie",refreshToken);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);

                return ResponseEntity.ok().body(tokens);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/oauth2")
    public ResponseEntity<?> loginOAuth2OrSignUp(@AuthenticationPrincipal OAuth2User user){// ?
        System.out.println("OAuth2Login");
        return null;
    }

    @PostMapping("/signUpCommon")
    public ResponseEntity<?> signUp(@RequestBody RegistrationFormDTO formDTO){
        try {
            CommonClientDTO clientDTO = new CommonClientDTO(
                    formDTO.getEmail(),
                    List.of(Role.USER),
                    formDTO.getAppPassword());

            requests.tryLoginToMail(clientDTO);
            requests.signUpInDatabase(formDTO,clientDTO);
            return ResponseEntity.ok().body(requests.getTokens(clientDTO));
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus
                            .NON_AUTHORITATIVE_INFORMATION.value())
                    .build();
        }
    }
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,
                                          HttpServletResponse response){
        try{
            requests.refreshToken(request,response);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
           // logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
