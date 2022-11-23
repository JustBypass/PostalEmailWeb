package com.mail.settings.entity;

import com.mail.settings.domain.Theme;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class SettingsEntity {
    @Id
    private String email;

    @Column
    @Enumerated
    private Theme theme;

    @CollectionTable
    @ElementCollection
    private Set<String> blockedUsers;
}
