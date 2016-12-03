package com.netcracker.paladin.infrastructure.services.email;

import com.netcracker.paladin.domain.MessageEntry;
import com.netcracker.paladin.domain.SignedPublicKeyEntry;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

/**
 * Created by ivan on 02.12.16.
 */
public interface EmailService {
    void sendEmail(String toAddress,
                   String subject,
                   String message,
                   byte[] cipherBlob,
                   byte[] signature) throws AddressException, MessagingException, IOException;

    List<MessageEntry> readEmails();

    void sendSignedPublicKey(String toAddress, SignedPublicKeyEntry signedPublicKeyEntry);

    List<SignedPublicKeyEntry> readSignedPublicKeys();
}
