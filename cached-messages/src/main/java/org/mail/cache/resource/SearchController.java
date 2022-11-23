package org.mail.cache.resource;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.search.input.SearchGlobalFilterDTO;
import com.mail.global.search.input.SearchLocalFilterDTO;
import org.mail.cache.domain.Envelope;
import org.mail.cache.service.CachedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    private CachedService cachedService;
    @GetMapping("/global")
    public List<Envelope> global(@RequestBody SearchGlobalFilterDTO filterDTO,
                                 @AuthenticationPrincipal ClientDTO clientDTO){
        return cachedService.globalSearch(filterDTO,clientDTO);
    }

    @GetMapping("/local")
    public List<Envelope> local(@RequestBody SearchLocalFilterDTO filterDTO,
                                @AuthenticationPrincipal ClientDTO clientDTO){
        return  cachedService.localSearch(filterDTO,clientDTO);
    }
 }
