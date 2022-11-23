package com.mail.database.domain;


import com.mail.global.clients.authorities.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Client {
    @Id
    private String email;

    @Column(unique = true)
    private String password;

    @Column
    @Enumerated
    @ElementCollection(targetClass = Role.class)
    private List<Role> roles = new ArrayList<>();
}
