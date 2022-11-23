package com.postal.testing.requests;

import com.mail.database.DatabaseMain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DatabaseMain.class,})
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ControllersTest {
  //  @Autowired
  //  private MockMvc mockMvc;

    @Test
    public void database_users_retrieve() throws Exception {
      //  mockMvc.perform(get("/users/online")).andExpect(status().isOk());
    }

    @Test
    public void jwtToken_check_expect_ok() throws Exception {
      //  mockMvc.perform(get("/token/v1/validate").header(HttpHeaders.AUTHORIZATION,"token")).andExpect(status().isOk());
    }
}
