package com.mail.notes.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @GetMapping("/{email}/{folder}")
    public ResponseEntity<?> notes(@PathVariable("email")String email, @PathVariable("folder")String folder){
        /*List<Envelope>*/
        return null;
    }
}
