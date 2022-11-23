package com.mail.control.mail;

import com.mail.control.utills.Constants;
import com.mail.global.dto.*;
import com.mail.global.clients.online.ClientDTO;
import com.mail.global.search.mail.EnvelopeOperationsInfoDTO;
import com.mail.control.domain.SendMailDTO;
import com.mail.control.domain.ServerMailPort;
import com.mail.control.requests.CacheRequests;
import com.mail.control.requests.SpamRequests;
import com.mail.control.utills.MailUtils;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.smtp.SMTPTransport;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MailAdminImpl {

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private CacheRequests cacheRequests;

    @Autowired
    private SpamRequests spamRequests;

    @Autowired
    private ServerMailPort serverMailPort;

    private final Logger logger = LoggerFactory.getLogger(MailAdminImpl.class);

    public void deleteMessages(MarkMailDTO  envelopes,
                                      ClientDTO clientDTO){
        try {
            Store store = mailUtils.getStore(clientDTO);
            Folder folder = store.getFolder(serverMailPort.getRealFolder(envelopes.getFolder(),clientDTO.getEmail()));
            Arrays.stream(envelopes.getMessages()).forEach (
                    env-> withFlagsOperation(env,folder,env.getUuid(),Flags.Flag.DELETED)
            );
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
    }

    private void withFlagsOperation(MarkMailDTO.MailInner envelope, Folder folder, long uuid, Flags.Flag flagsOperation) {
        try {
            Message message = ((UIDFolder) folder).getMessageByUID(uuid);
            message.setFlag(flagsOperation,envelope.isMailOperationQuota());
        }
        catch(Exception e){
            logger.info(e.getMessage());
        }
    }

    public void markAsReadAll(ClientDTO clientDTO, MarkMailDTO markMailDTO)  {
        try {
            Store connection = mailUtils.getStore(clientDTO);
            var folder = (UIDFolder) connection.getFolder(serverMailPort
                    .getRealFolder(clientDTO.getEmail(),
                            markMailDTO.getFolder()));// because we have the same folder

            Arrays.stream(markMailDTO.getMessages()).forEach(
                    mail -> {
                        markAsRead(connection, folder, mail);
                    }
            );
        }catch (Exception e){
            logger.warn(e.getMessage());
        }
    }
    public void markAsRead(Store store,UIDFolder folder, MarkMailDTO.MailInner  message) {
        try {
            folder.getMessageByUID(message.getUuid())
                    .setFlag(Flags.Flag.SEEN,
                            message.isMailOperationQuota());
        }
        catch (MessagingException messagingException){
            logger.warn(messagingException.getMessage());
        }
    }

    public String[] folders(String email) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        return new String[] { /// Раздробить функцию
                serverMailPort.getRealFolder("INBOX",email),
                serverMailPort.getRealFolder("SENT",email),
                serverMailPort.getRealFolder("NOTE",email),
                serverMailPort.getRealFolder("BASKET",email),
                serverMailPort.getRealFolder("SPAM",email)
        };
    }
    public void fillMessages(ClientDTO client)  {
        try {
            String email = client.getEmail();
            String[] folders = folders(email);
            String[] initialFolders = Constants.folders; //todo
            AtomicInteger i = new AtomicInteger();
            Store store = mailUtils.getStore(client);
            Arrays.stream(initialFolders).forEach(folder -> {
                        List<Envelope> envelopeList =
                                getMessages(store, email, folder, folders[i.getAndIncrement()]);
                        if (!envelopeList.isEmpty()) {
                            cacheRequests.fillCacheInFolder(envelopeList,folder,client.getEmail());
                        }
                    }
            );

            store.close();
        }catch(Exception e){
            logger.warn(e.getMessage());
        }
    }

    public void addTopic(ClientDTO clientDTO,String newFolderName){}

    public void fillMessages(ClientDTO[] clients){
        Arrays.stream(clients).forEach(
                this::fillMessages
        );
    }
/*    public int[] getCountOfAllTopicMessages(ClientDTO clientDTO) {
        try {
            Store store = mailUtils.getStore(clientDTO);
            String email = clientDTO.getEmail();

            String[] folders = folders(email);

            int[] count = new int[folders.length];
            for (int i = 0; i < folders.length; i++) {
                count[i] = store.getFolder(folders[i]).getMessageCount();
            }
            return count;
        } catch (Exception e) {
            return new int[0];
        }
    }*/
    /*
    public Envelope getMessageByUID(PersonalMailDTO personalMailDTO) {
        try {
            String email = personalMailDTO.getEmail();
            String password = personalMailDTO.getAppPassword();
            ServerMailPort.RealServerInfo main =
                    serverMailPort.getHostByEmail(email);
            String port = main.getImapPort();
            String host = main.getImapServer();
            String realFolder = serverMailPort
                    .getRealFolder(email,
                            personalMailDTO.getFolder());
            long uuid = personalMailDTO.getUuid();

            Properties props = mailUtils.getImapProperties(port, host);

            Session session = Session.getInstance(props);
            session.setDebug(true);
            Store store = session.getStore(IMAP);
            store.connect(host, email, password);

            Folder fold = store.getFolder(realFolder);

            fold.open(Folder.READ_WRITE);
            UIDFolder uf = (UIDFolder) fold;

            Message message = uf.getMessageByUID(uuid);
            message.setFlag(Flags.Flag.SEEN, true);


            cacheRequests.setSeenStatus(new EnvelopeOperationsInfoDTO[]{
                    EnvelopeOperationsInfoDTO
                            .builder()
                            .email(email)
                            .folder(realFolder)
                            .uuid(uuid)
                            .build()} , true);

            return getEnvelopeByMessage(uuid, message, true);
        } catch (Exception e) {
            return null;
        }
    }
*/

    public Envelope getEnvelopeByMessage(long uid,
                                                Message message,
                                                boolean getAttachment) throws MessagingException, IOException {
     // rewrite via stream api!

        Envelope env = Envelope
                .builder()
                .build();
        env.setUid(uid);
        if (message.getFlags().contains(Flags.Flag.SEEN)) {
            env.setRead(true);
        }
        if (message.isMimeType(MediaType.MULTIPART_MIXED_VALUE)) {
            Multipart mp = (Multipart) message.getContent();
            if (mp.getCount() > 1) {
                env.setHasAttachment(true);
                if (getAttachment) {
                    mailUtils.getAttachmentFromMultipart(mp, env);
                }
            }
        }
        String subject = message.getSubject();

        Date sent = message.getSentDate();

        env.setFrom(decodeAddress(message.getFrom()[0].toString()));

        env.setToWhom(Set.of( mailUtils.getCleanString(message, Message.RecipientType.TO)));
        env.setCc(Set.of(mailUtils.getCleanString(message, Message.RecipientType.CC)));
        env.setBcc(Set.of(mailUtils.getCleanString(message, Message.RecipientType.BCC)));

        env.setSentDate(sent);

        env.setBodyText(mailUtils.getText(message, true));

        env.setSubject(subject);
        return env;
    }

/*
    public List<Envelope> get(ClientDTO[] clientDTOS){
        List<Envelope> envelopeList = new ArrayList<>();
        Arrays.stream(clientDTOS).forEach(
             client->
                 envelopeList
                         .addAll(this.getAllPassingOnlyClient(client))
        );
        return envelopeList;
    }

    private List<Envelope> getAllPassingOnlyClient(ClientDTO clientDTO){
        return new ArrayList<>();
    }
*/

    public List<Envelope> getMessages(Store store, String email, String folder, String folderOriginal) {
        try {
            Folder f = store.getFolder(folder);

            f.open(Folder.READ_WRITE);
            UIDFolder uf = (UIDFolder) f;

            List<Envelope> envelopes = new ArrayList<>();
            Message[] mesgs = f.getMessages();
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(IMAPFolder.FetchProfileItem.FLAGS);
            fp.add(IMAPFolder.FetchProfileItem.CONTENT_INFO);

            f.fetch(mesgs, fp);
            Arrays.stream(mesgs).forEach(
                    message -> {
                        try {
                            Envelope env;
                            boolean isSpam = false;
                            if (!folder.equals("INBOX")) {
                                env = getEnvelopeByMessage(uf.getUID(message), message, false);
                            } else {
                                env = getEnvelopeByMessage(uf.getUID(message), message, true);
                                isSpam = spamRequests
                                        .checkIfMessageIsSpam(
                                                EnvelopeSpamFilterDTO.builder().envelope(env)
                                                        .from(env.getFrom())
                                                        .to(email)
                                                        .spamFolderEmailDTO(RemovingToSpamFolderEmailDTO.builder()
                                                                .email(email)
                                                                .spamFolder(serverMailPort.getRealFolder("SPAM", email))
                                                                .uuid((int) uf.getUID(message))
                                                                .folder(folderOriginal)
                                                                .build())
                                                        .build());
                                if (isSpam) {
                                    mailUtils.removeEmailToSpam(message, f);
                                }
                            }
                            if (!isSpam) envelopes.add(env);// add ?
                        }
                        catch(Exception e){
                            logger.error("getMessages critical error occurred");
                        }
                    }
            );
            return envelopes;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return null;
        }
    }


    public String decodeAddress(String emailId) throws UnsupportedEncodingException {
        String string = "";
        try{
            string =  MimeUtility.decodeWord(emailId);
        }catch(ParseException e){
            logger.info("ParseException occured so return the same string");
            string = emailId;
        }
        return string;
    }

    public void sendMailCheckToChangePort(SendMailDTO dto) throws MessagingException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Post post = dto.getPost();
        String email = dto.getClientDTO().getEmail();
        Session session = mailUtils
                .getSessionForSmtp(dto.getServerInfo()
                                .getSmtpServer(),
                        dto.getServerInfo()
                                .getSmtpPort());
        SMTPTransport transport = mailUtils.getSMTPConnection(session, dto);

        MimeMessage message = new MimeMessage(session);

        mailUtils.acceptMessageRecipients(post.getTo().toArray(String[]::new),message, Message.RecipientType.TO);
        mailUtils.acceptMessageRecipients(post.getCc().toArray(String[]::new),message, Message.RecipientType.CC);
        mailUtils.acceptMessageRecipients(post.getBcc().toArray(String[]::new),message, Message.RecipientType.BCC);

        message.setFrom(new InternetAddress(email));
        message.setSubject(post.getSubject());
        message.setSentDate(new Date());
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(post.getBodyText(), MediaType.TEXT_HTML_VALUE);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        Set<byte[]> files = post.getFiles();

        if(files != null && files.size() > 0){
            for (var bytes : files) {
                MimeBodyPart attachPart = new MimeBodyPart();
                DataSource ds = new ByteArrayDataSource(bytes,"application/octet-stream");
                attachPart.setDataHandler(new DataHandler(ds));
              //  attachPart.setDisposition(Part.ATTACHMENT);
                multipart.addBodyPart(attachPart);
            }
        }

        message.setContent(multipart);
        transport.sendMessage(message,message.getAllRecipients());

        transport.close();
    }


    public void send(Map.Entry<ClientDTO,List<Post>> entry){
        ServerMailPort.RealServerInfo serverInfo;
        String email = entry.getKey().getEmail();
        ClientDTO clientDTO = entry.getKey();
        try{
            serverInfo =  serverMailPort.getHostByEmail(email);
            entry.getValue().forEach(
                    post->{
                        try {
                            sendMail(SendMailDTO.builder()
                                    .clientDTO(clientDTO)
                                    .post(post)
                                    .serverInfo(serverInfo)
                                    .build());
                        }catch(Exception e){
                            logger.warn(e.getMessage());
                        }
                    }
            );

        }
        catch(Exception e){
            logger.warn(e.getMessage());
        }
    }

    public void sendAll(List<Map.Entry<ClientDTO, List<Post>>> list){
       list.forEach(this::send); // todo think how to do this parallel
    }

    public void sendMail(SendMailDTO dto) throws MessagingException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        try {
            sendMailCheckToChangePort(dto);
        } catch (Exception e) {
            dto.getServerInfo().setSmtpPort("25");
            sendMailCheckToChangePort(dto);
        }
    }
}
