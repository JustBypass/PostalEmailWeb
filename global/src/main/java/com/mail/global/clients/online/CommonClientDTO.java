package com.mail.global.clients.online;

import com.fasterxml.jackson.annotation.JsonView;
import com.mail.global.clients.authorities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CommonClientDTO extends ClientDTO implements Serializable{
    @JsonView
    private String appPassword;

    public CommonClientDTO(String email,List<Role> roles,  String appPassword) {
        super(email,roles);
        this.appPassword = appPassword;
    }

}
