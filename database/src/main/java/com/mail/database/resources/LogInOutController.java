package com.mail.database.resources;

import com.mail.global.dto.LoginForm;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class LogInOutController {

    @GetMapping("/login")
    public ResponseEntity<?> login(){
        return null;
    }

    @GetMapping("/registration")
    @Transactional
    public ResponseEntity<?> login(@RequestBody LoginForm form){
        if(form == null){
            throw new NullPointerException("Login form is null");
        }
        // add to online users
        return null;
    }

}
