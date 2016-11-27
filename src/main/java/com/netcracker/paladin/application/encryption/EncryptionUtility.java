package com.netcracker.paladin.application.encryption;

/**
 * Created by ivan on 27.11.16.
 */
public interface EncryptionUtility {
    String encryptEmail(String plainText, String recipient);

    String decryptEmail(String cipherText);
}
