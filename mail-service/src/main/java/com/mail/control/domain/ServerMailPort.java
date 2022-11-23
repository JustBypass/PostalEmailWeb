package com.mail.control.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ServerMailPort { // We need app password everywhere except of gmail

    @Data
    public static class RealServerInfo{
        private String imapServer;
        private String smtpServer;
        private String folder;
        private String imapPort;
        private String smtpPort;
    }

    public static class MAIL{ // We need app password

        private static final short SMTP_PORT = 465;

        private static final short IMAP_PORT = 993 ;

        private static final String IMAP_SERVER = "imap.mail.ru";

        private static final String SMTP_SERVER = "smtp.mail.ru";

        private static final String INBOX_FOLDER = "INBOX";

        private static final String SENT_FOLDER = "Отправленные";

        private static final String SPAM_FOLDER = "Спам";

        private static final String NOTE_FOLDER = "Черновики";

        private static final String BASKET_FOLDER = "Корзина";
    }

    public static class GMAIL{
        private static final short SMTP_PORT = 587;

        private static final short IMAP_PORT = 993 ;

        private static final String IMAP_SERVER = "imap.gmail.com";

        private static final String SMTP_SERVER = "smtp.gmail.com";

        private static final String INBOX_FOLDER = "INBOX";

        private static final String SENT_FOLDER = "Sent";

        private static final String SPAM_FOLDER = "Spam";

        private static final String NOTE_FOLDER = "Notes";
    }

    public static class YAHOO{
        private static final short SMTP_PORT = 587;

        private static final short IMAP_PORT = 993 ;

        private static final String IMAP_SERVER = "imap.mail.yahoo.com";

        private static final String SMTP_SERVER = "smtp.mail.yahoo.com";

        private static final String INBOX_FOLDER = "INBOX";

        private static final String SENT_FOLDER = "Sent";

        private static final String SPAM_FOLDER = "Spam";

        private static final String NOTE_FOLDER = "Notes";
    }

    public static class YANDEX{ // SSL
        private static final short SMTP_PORT = 465;

        private static final short IMAP_PORT = 993 ;

        private static final String IMAP_SERVER = "imap.mail.ru";

        private static final String SMTP_SERVER = "smtp.mail.ru";

        private static final String INBOX_FOLDER = "INBOX";

        private static final String SENT_FOLDER = "Sent";

        private static final String SPAM_FOLDER = "Spam";

        private static final String NOTE_FOLDER = "Notes";
    }

    public void getRealFolder(HashMap<String, String> props ,Class<?> server,String folder) throws NoSuchFieldException, IllegalAccessException {
        props.put("folder",
                server.getDeclaredField(folder.toUpperCase() + "_FOLDER")
                        .get(null)
                        .toString());
    }

    public String getRealFolder(String folder,String email) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        return getServer(email)
                .getDeclaredField(folder.toUpperCase() + "_FOLDER")
                .get(null)
                .toString();
    }

    public Class<?> getServer(String email) throws ClassNotFoundException {
        String[] host = email.split("@")[1].split("\\."); //noname@mail.ru <--mail
        String path = "com.mail.control.domain.ServerMailPort$"+host[0].toUpperCase();
        return Class.forName(path);
    }

    public RealServerInfo getHostByEmail(String email) throws  ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        RealServerInfo info = new RealServerInfo();
        Class<?> server = getServer(email);

        info.setImapServer( server.getDeclaredField("IMAP_SERVER").get(null).toString());
        info.setSmtpServer( server.getDeclaredField("SMTP_SERVER").get(null).toString());

        info.setImapPort(  String.valueOf(server.getDeclaredField("SMTP_PORT").getShort(null)));
        info.setSmtpPort(String.valueOf(server.getDeclaredField("IMAP_PORT").getShort(null)));

        return info;
    }
}
