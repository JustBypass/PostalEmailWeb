package com.mail.database.domain;


import com.mail.global.clients.authorities.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCommonDTO extends UserDTO   {
    private String appPassword;


    public UserCommonDTO(String email, String appPassword, List<Role> roles){
        super(email,roles);
        this.appPassword = appPassword;
    }

    public UserCommonDTO() {
        super("em",List.of(Role.USER));
        this.appPassword = "";
    }
}
