package com.mail.database.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mail.global.clients.authorities.Role;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCommonDTO.class, name = "child_a"),
        @JsonSubTypes.Type(value = UserOAuth2DTO.class, name = "child_b")
})
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String email;

    public UserDTO(){
        this.email = "";
    }

    public UserDTO(String email,List<Role> roles){
        this.roles.addAll(roles);
        this.email = email;
    }
    /*@Column(name="someTypeCd", nullable = false, columnDefinition="enum('USER', 'ADMIN', 'MODERATOR', 'BLOCKED')")
    @Enumerated///(EnumType.ORDINAL)
    @ElementCollection(targetClass = Role.class)
    @CollectionTable*/
    @Column
    @Enumerated
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
