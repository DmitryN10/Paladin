package com.netcracker.paladin.application.encryption.asymmetric;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ivan on 27.11.16.
 */
public interface AsymmetricEncryption {
    KeyPair generateKeyPair();

    PublicKey generatePublicKey(PrivateKey privateKey);

    byte[] encrypt(String text, PublicKey key);

    String decrypt(byte[] text, PrivateKey key);
}
