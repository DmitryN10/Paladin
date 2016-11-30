package com.netcracker.paladin.infrastructure.services.email;

import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.infrastructure.services.config.ConfigService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailService {

    private final ConfigService configService;

    public EmailService(ConfigService configService) {
        this.configService = configService;
    }

    public void sendEmail(String toAddress,
                          String subject,
                          String message,
                          byte[] cipherBlob) throws AddressException, MessagingException, IOException {

        Properties smtpProperties = configService.loadProperties();
        final String username = smtpProperties.getProperty("mail.user");
        final String password = smtpProperties.getProperty("mail.password");
        final String hostname = smtpProperties.getProperty("mail.smtp.host");
        final String port = smtpProperties.getProperty("mail.smtp.port");

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(hostname);
            email.setSmtpPort(Integer.parseInt(port));
            System.out.println(Integer.parseInt(port));
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSSLOnConnect(true);
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(toAddress);

//            InputStream is = new BufferedInputStream(new ByteArrayInputStream(cipherBlob));
            DataSource source = new ByteArrayDataSource(cipherBlob, "application/python-pickle");
            email.attach(source, "Chebi", "Naum molodec");

            email.send();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    public List<MessageEntry> readEmails() {
        try {
            Properties properties = configService.loadProperties();
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("pop3s");
            store.connect(
                    properties.getProperty("mail.smtp.host"),
                    properties.getProperty("mail.user"),
                    properties.getProperty("mail.password"));
            
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            List<MessageEntry> messageEntryList = new ArrayList<>(messages.length);
            
            for(Message message : messages){

                MessageEntry messageEntry = new MessageEntry();

                messageEntry.setFrom(message.getFrom()[0].toString());
                messageEntry.setSubject(message.getSubject());
                messageEntry.setMessage(getText(message));
                messageEntry.setSentDate(message.getSentDate());

                String contentType = message.getContentType();
                if (contentType.contains("multipart")) {
                    Multipart multiPart = (Multipart) message.getContent();
                    for (int i = 0; i < multiPart.getCount(); i++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            if(part.getFileName().equals("Chebi")){
                                byte[] cipherBlob = IOUtils.toByteArray(part.getInputStream());
                                messageEntry.setCipherBlob(cipherBlob);
                            }
                        }
                    }
                }

                messageEntryList.add(messageEntry);
            }
            
            return messageEntryList;
            
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
//            textIsHtml = p.isMimeType("text/html");
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
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
}
