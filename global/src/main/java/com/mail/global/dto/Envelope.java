package com.mail.global.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Envelope implements Serializable {
    private Set<String>toWhom;
    private Set<String> cc;
    private Set<String> bcc;
    private String from;
    private String subject;
    private Date receivedDate;
    private Date sentDate;
    private boolean hasAttachment = false;
    private boolean newMessage = false;
    private boolean isRead = false;
    private String host;

    private long uid;
    private String folder;
    private String bodyText;
    private boolean marked ;
    public Set<byte[]> attachment;

    // private Set<Envelope> chain; todo chain
}