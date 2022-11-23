package org.mail.cache.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Indexed(index = "EnvelopeIndex")
public class Envelope implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    private String host;

    @Column(name = "fr")
    private String from;

    @Column
    private boolean marked = false;

    @Column
    private boolean isRead = false;

    @Column
    @Temporal(TemporalType.DATE)
    @DateBridge(resolution = Resolution.DAY)
    private Date receivedDate;

    @Column
    @Temporal(TemporalType.DATE)
    @DateBridge(resolution = Resolution.DAY)
    private Date sentDate;

    @Field(index= Index.YES, analyze=Analyze.YES, store=Store.YES)
    private String bodyText;

   @Field(index= Index.YES, analyze=Analyze.YES, store=Store.YES)
   private String subject;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private Set<String> toWhom = new HashSet<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private Set<String> cc= new HashSet<>();

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "bcct_envelope_id"),name ="bcc_id" )
   private Set<String> bcc= new HashSet<>();

    @ElementCollection(targetClass = byte[].class,fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "attachment_envelope_id"),name ="attachment_id" )
    private Set<byte[]> attachment= new HashSet<>();
}
