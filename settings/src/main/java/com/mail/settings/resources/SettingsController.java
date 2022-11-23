package com.mail.settings.resources;

import com.mail.global.clients.online.ClientDTO;
import com.mail.settings.domain.ConfigurationSettingsDTO;
import com.mail.settings.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private SettingsRepository settingsRepository;
    @PostMapping
    public ResponseEntity<?> set(@RequestBody ConfigurationSettingsDTO newSettings){
        if(newSettings == null||newSettings.getEmail() == null){
            throw new NullPointerException("Invalid settings data passed through ");
        }
       return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal ClientDTO clientDTO) {
        return ResponseEntity.ok().build();
    }
}
