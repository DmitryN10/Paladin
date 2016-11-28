package com.netcracker.paladin.application.encryption.symmetric;

/**
 * Created by ivan on 27.11.16.
 */
public interface SymmetricEncryption {

    void setKey(byte[] key);

    byte[] encrypt(byte[] sequenceToEncrypt, byte[] sessionKey);

    byte[] decrypt(byte[] sequenceToDecrypt, byte[] sessionKey);
}
