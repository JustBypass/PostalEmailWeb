package com.postal.scheduled.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ScheduledPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    private LocalDate dateToSend;
    @OneToOne
    private Post post;
}
