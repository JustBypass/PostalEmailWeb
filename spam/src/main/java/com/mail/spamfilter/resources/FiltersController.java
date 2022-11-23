package com.mail.spamfilter.resources;

import com.mail.global.dto.Envelope;
import com.mail.global.methods.WithStreamMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("filter")
public class FiltersController {
    @Autowired
    private WithStreamMethods withStreamMethods;

    private final Logger logger = LoggerFactory.getLogger(FiltersController.class);
    @PostMapping
    public Boolean filtrate(@RequestBody String envelopeString){
        Envelope envelope = withStreamMethods.convertFrom(envelopeString);
        if(envelope == null){
            throw new NullPointerException("Envelope is null");
        }
        logger.info("Filtering");
        return false;
    }
}
