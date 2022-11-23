package com.mail.global.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EnvelopeSpamFilterDTO implements Serializable {
    private Envelope envelope;
    private String from;
    private String to;
    private RemovingToSpamFolderEmailDTO spamFolderEmailDTO;
}
