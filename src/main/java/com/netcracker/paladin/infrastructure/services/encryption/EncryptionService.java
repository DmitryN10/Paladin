package com.netcracker.paladin.infrastructure.services.encryption;

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
}
