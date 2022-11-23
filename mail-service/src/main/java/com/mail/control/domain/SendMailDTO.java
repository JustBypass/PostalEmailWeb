package com.mail.control.domain;

import com.mail.global.dto.Post;
import com.mail.global.clients.online.ClientDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class SendMailDTO implements Serializable {
    private Post post;
    private ClientDTO clientDTO;
    private ServerMailPort.RealServerInfo serverInfo;
}
