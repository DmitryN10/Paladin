package com.netcracker.paladin.infrastructure.services.encryption.asymmetric;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ivan on 27.11.16.
 */
public interface AsymmetricEncryption {
    byte[] generatePrivateKey();

    byte[] generatePublicKey(byte[] privateKeyBytes);

    byte[] encrypt(byte[] sequenceToEncrypt, PublicKey publicKey);

    byte[] encrypt(byte[] sequenceToEncrypt, byte[] publicKeyBytes);

    byte[] decrypt(byte[] sequenceToDecrypt, PrivateKey privateKey);

    byte[] decrypt(byte[] sequenceToDecrypt, byte[] privateKeyBytes);
}
