package com.mail.global.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Set;

@Data
public class Post implements Serializable {
    private String from;
    private Set<String> to;
    private Set<String> cc;
    private Set<String> bcc;

    private String subject;

    private String bodyText;

    private Set<byte[]> files;
}
