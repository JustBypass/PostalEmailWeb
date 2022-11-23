package com.mail.global.search.mail;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EnvelopeOperationsInfoDTO implements Serializable {
    private String folder;
    private long uuid;
    //private String email;
}
