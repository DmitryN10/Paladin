package com.netcracker.paladin.application.encryption;

import java.security.KeyPair;

/**
 * Created by ivan on 27.11.16.
 */
public interface EncryptionUtility {
    abstract byte[] encryptEmail(String plainText, String recipient);

    String decryptEmail(byte[] cipherTextAndSessionKey);

    KeyPair getKeyPair();
}
