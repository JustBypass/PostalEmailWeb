package com.mail.global.clients.online;


import com.fasterxml.jackson.annotation.JsonView;
import com.mail.global.clients.authorities.Role;
import com.mail.global.clients.online.ClientDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ClientOAuth2DTO extends ClientDTO implements Serializable{
    @JsonView
    private String accessToken;
    @JsonView
    private String refreshToken;
    public ClientOAuth2DTO(String email,List<Role> roles, String accessToken, String refreshToken) {
        super(email,roles);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
