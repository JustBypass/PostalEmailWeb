package com.mail.database.domain;

import com.mail.global.clients.authorities.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UserOAuth2DTO extends UserDTO  {
    private String accessToken;
    private String refreshToken;

    public UserOAuth2DTO(String email, String accessToken, String refreshToken, List<Role> roles){
        super(email,roles);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public UserOAuth2DTO() {
        super("hoo",List.of(Role.USER));
        this.refreshToken = "";
        this.accessToken = "";
    }
}
