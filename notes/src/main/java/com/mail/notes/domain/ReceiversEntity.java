package com.mail.notes.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="receivers")
@Data
public class ReceiversEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="envelopes_id",nullable = false)
    private EnvelopeLocal envelope;
}
