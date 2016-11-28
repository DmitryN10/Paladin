package com.netcracker.paladin.infrastructure.mail;

import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.infrastructure.ConfigUtility;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class EmailUtility {

    private final ConfigUtility configUtility;

    public EmailUtility(ConfigUtility configUtility) {
        this.configUtility = configUtility;
    }

    public void sendEmail(String toAddress,
                          String subject,
                          String message,
                          byte[] cipherBlob) throws AddressException, MessagingException, IOException {

        Properties smtpProperties = configUtility.loadProperties();
        final String username = smtpProperties.getProperty("mail.user");
        final String password = smtpProperties.getProperty("mail.password");
        final String hostname = smtpProperties.getProperty("mail.smtp.host");
        final String port = smtpProperties.getProperty("mail.smtp.port");

        try {
            MultiPartEmail email = new MultiPartEmail();
            email.setHostName(hostname);
            email.setSmtpPort(Integer.parseInt(port));
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSSLOnConnect(true);
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(toAddress);

//            InputStream is = new BufferedInputStream(new ByteArrayInputStream(cipherBlob));
            DataSource source = new ByteArrayDataSource(cipherBlob, "text/plain");
            email.attach(source, "Chebi", "Naum molodec");

            email.send();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    public List<MessageEntry> readEmails() {
//        try {
//            //create properties field
//            Properties properties = configUtility.loadProperties();
//            Session emailSession = Session.getDefaultInstance(properties);
//
//            //create the POP3 store object and connect with the pop server
//            Store store = emailSession.getStore("pop3s");
//
//            store.connect(
//                    properties.getProperty("mail.smtp.host"),
//                    properties.getProperty("mail.user"),
//                    properties.getProperty("mail.password"));
//
//            // create the folder object and open it
//            Folder emailFolder = store.getFolder("INBOX");
//            emailFolder.open(Folder.READ_ONLY);
//
////            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//
//            // retrieve the messages from the folder in an array and print it
//            Message[] messages = emailFolder.getMessages();
//            List<MessageEntry> messageContainerList = new ArrayList<>(messages.length);
//
//            System.out.println("Size messages: "+messages.length);
//
//            for(Message message : messages){
//                MessageEntry messageContainer = new MessageEntry();
//                messageContainer.setFrom(message.getFrom()[0].toString());
//                messageContainer.setSubject(message.getSubject());
//                messageContainer.setText(getText(message));
//                messageContainerList.add(messageContainer);
//            }
//
//            System.out.println("Size: "+messageContainerList.size());
//
////            for (int i = 0; i < messages.length; i++) {
////                Message message = messages[i];
////                System.out.println("---------------------------------");
////                writePart(message);
////                String line = reader.readLine();
////                if ("YES".equals(line)) {
////                    message.writeTo(System.out);
////                    message.
////                } else if ("QUIT".equals(line)) {
////                    break;
////                }
////            }
//
//
//
//            // close the store and folder objects
//            emailFolder.close(false);
//            store.close();
//
//            return messageContainerList;
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//            throw new IllegalStateException();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            throw new IllegalStateException();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new IllegalStateException();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IllegalStateException();
//        }
        return null;
    }

//            //create the folder object and open it
//            Folder emailFolder = store.getFolder("INBOX");
//            emailFolder.open(Folder.READ_ONLY);
//
//            // retrieve the messages from the folder in an array and print it
//            Message[] messages = emailFolder.getMessages();
//            System.out.println("messages.length---" + messages.length);
//
//            for (int i = 0, n = messages.length; i < n; i++) {
//                Message message = messages[i];
//                System.out.println("---------------------------------");
//                System.out.println("Email Number " + (i + 1));
//                System.out.println("Subject: " + message.getSubject());
//                System.out.println("From: " + message.getFrom()[0]);
//                System.out.println("Text: " + message.getContent().toString());
//
//            }
//
//            //close the store and folder objects
//            emailFolder.close(false);
//            store.close();
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
