package org.mail.cache.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.global.methods.WithStreamMethods;
import org.mail.cache.domain.Envelope;
import org.mail.cache.repository.CachedMessagesRepository;
import org.mail.cache.service.CachedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("cache")
public class FillCacheController {
    @Autowired
    private CachedService cachedService;
    @PostMapping("/fill/{folder}/{email}")
    public ResponseEntity<?> fill(@RequestBody List<com.mail.global.dto.Envelope> list,
                                  @PathVariable("folder") String folder,
                                  @PathVariable("email") String email) throws IOException {
        cachedService.fillCache(list,folder,email);
        return ResponseEntity.ok().build();
    }
}
