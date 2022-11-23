package com.mail.global.search.input;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchGlobalFilterDTO implements Serializable {
    private boolean hasAttachment;
    private String[] containsWords;
    private String[] containsEmails;
    private String[] from;
    private String[] to;
    private String subject;
    private String folder;
}
