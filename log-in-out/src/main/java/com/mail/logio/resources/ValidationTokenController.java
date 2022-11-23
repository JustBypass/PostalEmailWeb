package com.mail.logio.resources;



import com.mail.logio.requests.Requests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token/v1")
public class ValidationTokenController{
    @Autowired
    private Requests requests;
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        try {
            return ResponseEntity.ok().body(requests.getClientFromJwt(accessToken));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}