package com.netcracker.paladin.application.encryption;

/**
 * Created by ivan on 27.11.16.
 */
public interface EncryptionUtility {
    abstract byte[] encryptEmail(String plainText, String recipient);

    String decryptEmail(byte[] cipherTextAndSessionKey);

    byte[] generatePrivateKey();

    void setPrivateKey(byte[] privateKey);

    byte[] getPublicKey();
}
