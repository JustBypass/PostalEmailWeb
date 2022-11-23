package com.mail.control.utills;

import com.mail.global.clients.online.ClientDTO;
import com.mail.global.clients.online.ClientOAuth2DTO;
import com.mail.global.clients.online.CommonClientDTO;
import com.mail.global.dto.Envelope;
import com.mail.control.domain.SendMailDTO;
import com.mail.control.domain.ServerMailPort;
import com.sun.mail.smtp.SMTPTransport;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.mail.control.utills.Constants.*;

@Component
public class MailUtils {
    @Autowired
    private ServerMailPort serverMailPort;

    public SMTPTransport getSMTPConnection(Session session, SendMailDTO dto) throws MessagingException {
        ClientDTO clientDTO = dto.getClientDTO();
        SMTPTransport transport = (SMTPTransport)session
                .getTransport(SMTP);

        String email = clientDTO.getEmail();
        String tokenOrPassword = null;
        if(clientDTO instanceof ClientOAuth2DTO){
            ClientOAuth2DTO oauth2 =  ((ClientOAuth2DTO)clientDTO);
            tokenOrPassword = oauth2.getAccessToken();
        }
        else{
            CommonClientDTO commonClientDTO =  ((CommonClientDTO)clientDTO);
            tokenOrPassword = commonClientDTO.getAppPassword();
        }
        transport.connect(dto.getServerInfo().getSmtpServer(), email, tokenOrPassword);
        return transport;
    }

    public void removeEmailToSpam(Message message,Folder spam){

    }

    public void acceptMessageRecipients(String[] recipientList, Message message, Message.RecipientType type) throws MessagingException {
        if(recipientList.length == 0){
            return;
        }
        InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
        int counter = 0;
        for (String recipient : recipientList) {
            recipientAddress[counter++] = new InternetAddress(recipient.trim());
        }
        message.setRecipients(type, recipientAddress);
    }

    public Session getSessionForSmtp(String port, String host) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, NoSuchProviderException {
        Properties props = new Properties();

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.timeout", 1000);

        return Session.getInstance(props,null);
    }

    public Properties getImapOAuth2Properties(String port,String host){
        Properties props = new Properties();
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.imap.port", port);
        props.put("mail.imap.", host);
        props.setProperty("mail.store.protocol", "imap");
        props.put("mail.imap.fetchsize", "819200");
        return props;
    }

        public Properties getImapProperties(String port,String host){
        Properties props = new Properties();
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.imap.port", port);
        props.put("mail.imap.host", host);
        props.setProperty("mail.store.protocol", "imap");
        props.put("mail.imap.fetchsize", "819200");
        return props;
    }

    public String[] getCleanString(Message message, Message.RecipientType recipientType) throws MessagingException { /// todo with stream api
        return Arrays.stream(message.getRecipients(recipientType))
                .map(Address::toString)
                .map(
                        el -> Arrays.stream(el.split(" ")).findFirst().get()
                )
                .map(
                        el -> el.replaceAll("[<>]", "")
                ).toArray(String[]::new);
    }
    public String[] getCleanString(Message message) throws MessagingException { /// todo with stream api
        return Arrays.stream(message.getFrom())
                .map(Address::toString)
                .map(
                        el -> Arrays.stream(el.split(" ")).findFirst().get()
                )
                .map(
                        el -> el.replaceAll("[<>]", "")
                ).toArray(String[]::new);
    }

    public String getText(Part p,boolean textIsHtml) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp,textIsHtml);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp,textIsHtml);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp,textIsHtml);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i),textIsHtml);
                if (s != null)
                    return s;
            }
        }
        return null;
    }

    public Store getStore(ClientDTO clientDTO) throws MessagingException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        ServerMailPort.RealServerInfo main = serverMailPort.getHostByEmail(clientDTO.getEmail());
        String port = main.getImapPort();
        String host = main.getImapServer();

        Properties props = null;

        Store store = null;
        if(clientDTO instanceof ClientOAuth2DTO){
            props = getImapOAuth2Properties(port,host);
            Session session = Session.getInstance(props);
            store = session.getStore("imaps");
            /// token to base64 as in template
            store.connect(host,
                    clientDTO.getEmail(),
                    ((ClientOAuth2DTO)clientDTO)
                            .getAccessToken());
        }
        else if(clientDTO instanceof CommonClientDTO){
            props = getImapProperties(port,host);
            Session session = Session.getInstance(props);
            store = session.getStore("imaps");
            store.connect(host,((CommonClientDTO)clientDTO).getAppPassword());
        }
        else {
            throw new IllegalArgumentException("ClientDTO instance has no implementation yet");
        }
        return store;
    }
    public void login(ClientDTO clientDTO) throws MessagingException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        getStore(clientDTO);
    }

    public void getAttachmentFromMultipart(Multipart multipart, Envelope envelope) {
        try {
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bp = multipart.getBodyPart(i);
                if (!bp.getContentType().contains("alternative")) {
                    InputStream stream = bp.getInputStream();

                    byte[] buffer = new byte[512];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    int bytesRead;
                    while ((bytesRead = stream.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    envelope.attachment.add(baos.toByteArray());
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
