package org.mail.cache.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ToListEntity {
    @Id
    private Long id;
    @Column
    private long messageId;
    @Column
    private String email;
}
