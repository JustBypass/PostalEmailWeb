package com.postal.testing.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mail.database.DatabaseMain;
import com.mail.global.clients.online.ClientDTO;
import com.mail.database.domain.UserCommonDTO;
import com.mail.database.domain.UserDTO;
import com.mail.database.domain.UserOAuth2DTO;
import com.mail.database.repository.OnlineUsersRepository;
import com.mail.global.clients.authorities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

@SpringBootTest(classes = {DatabaseMain.class})
@ActiveProfiles("test")
public class ClientToUserTest {

    @Autowired
    private OnlineUsersRepository onlineUsersRepository;

    @Test
    public void try_to_convert_usersList_to_clientArray() throws IOException {
        UserDTO[] users = new UserDTO[]{
                new UserCommonDTO("userA","password1",List.of(Role.USER, Role.MODERATOR)),
                new UserOAuth2DTO("userB","access","refresh",List.of(Role.ADMIN)),
                new UserCommonDTO("userC","password2",List.of(Role.USER, Role.MODERATOR))
        };

        var obj =  onlineUsersRepository.saveAll(List.of(users));
        List<UserDTO> usr = onlineUsersRepository.findAll();

        var all = onlineUsersRepository.findAll();
        UserDTO[] p = all.toArray(UserDTO[]::new);

        var src = new ObjectMapper()
                .writeValueAsBytes(p);
        ClientDTO[] clients = new ObjectMapper()
                .readValue(src, ClientDTO[].class);
        System.out.println("");
    }

}
