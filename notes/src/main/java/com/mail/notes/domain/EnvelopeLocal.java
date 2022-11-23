package com.mail.notes.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="envelopes")
public class EnvelopeLocal {
    @Id
    private Long id;

    @OneToMany(mappedBy = "receivers")
    private List<ReceiversEntity> receivers;

    @Column
    private String from;
    @Column
    private String subject;
    @Column
    private Date receivedDate;
    @Column
    private Date sentDate;
    @Column
    private boolean hasAttachment = false;
    @Column
    private boolean newMessage = false;
    @Column
    private boolean seen = false;
    @Column
    private long uid;
    @Column
    private String bodyText;
    @Column
    private boolean marked;

    @CollectionTable
    @ElementCollection
    public List<byte[]> byteList = new ArrayList<>();
}
