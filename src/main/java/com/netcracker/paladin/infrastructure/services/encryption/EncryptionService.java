package com.netcracker.paladin.infrastructure.services.encryption;

import com.netcracker.paladin.domain.SignedPublicKeyEntry;

import java.util.List;

/**
 * Created by ivan on 27.11.16.
 */
public interface EncryptionService {
    abstract byte[] encryptEmail(String plainText, String recipient);

    String decryptEmail(byte[] cipherTextAndSessionKey);

    byte[] generatePrivateKey();

    void setPrivateKey(byte[] privateKey);

    byte[] getOwnPublicKey();

    void addPublicKey(String email, byte[] publicKey);

    void deletePublicKey(String email);

    List<String> getAllEmailsWithPublicKey();

    SignedPublicKeyEntry getSignedPublicKeyEntry(String email);

    boolean verifySignature(String email, byte[] signature, byte[] data);
}
