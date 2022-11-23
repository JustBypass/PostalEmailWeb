package com.mail.global.clients.online;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.mail.global.clients.authorities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.*;


@Data
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommonClientDTO.class,name = "child_a"),
        @JsonSubTypes.Type(value = ClientOAuth2DTO.class, name = "child_b")
})
public abstract class ClientDTO {
    public ClientDTO(String email,List<Role> roles){
        this.email = email;
        this.roles = roles;
    }

    @JsonView
    private String email;
    @JsonView
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(); // rewrite via stream API
        for(Role role:roles){
            for(GrantedAuthority authority:role.getAuthorities()){
                authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }
        }
        return authorities;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientDTO) {
            return email.equals(((ClientDTO)obj).email) ;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
