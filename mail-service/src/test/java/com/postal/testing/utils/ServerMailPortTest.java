package com.postal.testing.utils;


import com.mail.control.domain.ServerMailPort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class ServerMailPortTest {

    @Mock
    private ServerMailPort serverMailPort = new ServerMailPort();

    @Test(expected=ClassNotFoundException.class)
    public void checkIfUserEmailGetsWrongServerInfoExpectException() throws ClassNotFoundException {
        String testEmail = "burbonovich@gipsy.com";
        serverMailPort.getServer(testEmail);
    }

    @Test
    public void checkIfUserEmailGetsWrongServerInfoExpectGood() throws ClassNotFoundException {
        String testEmail = "burbonovich@gmail.com";

        assertThat(ServerMailPort.GMAIL.class).isEqualTo(serverMailPort.getServer(testEmail));
    }
}
