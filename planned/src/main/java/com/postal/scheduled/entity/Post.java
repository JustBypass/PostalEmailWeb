package com.postal.scheduled.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Post implements Serializable {
    @Id
    @JsonIgnore
    private Long id;

    @OneToOne
    private ScheduledPost scheduledEnvelope;

    @Column(name = "fr")
    private String from;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private Set<String> to;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private Set<String> cc;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private Set<String> bcc;

    private String subject;

    private String bodyText;

    @ElementCollection(targetClass = byte[].class,fetch = FetchType.EAGER)
    private Set<byte[]> files;

    // private long replyUuid;
}
