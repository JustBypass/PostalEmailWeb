package com.postal.testing.mail;

import com.mail.control.MailControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.stream.Stream;



@SpringBootTest(classes = {MailControl.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MailOperationsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void messages_from_addresses_splitter()  {
        String[] array = {"<nikisazikov56@gmail.com> hello.com", "vasyapupkin@mail.ru   ssshhh"};
        Stream<String> streamOfArray = Arrays.stream(array);

          String[] list = Arrays.stream(array)
                   .map(
                           el-> Arrays.stream(el.split(" ")).findFirst().get()
                   )
                   .map(
                           el->el.replaceAll("[<>]","")
                   ).toArray(size->new String[size]);

           Assertions.assertEquals(list.length,new String[]{
                "nikisazikov56@gmail.com",
                "vasyapupkin@mail.ru"
        }.length);
    }

    @Test
    public void read_mail_of_single_user(){

    }

    @Test
    public void mail_login_controller_check()  {
       // mockMvc.perform(post("/login").content(asJsonString(commonClientDTO))).andExpect(status().isCreated());
    }
}
