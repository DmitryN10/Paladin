package com.netcracker.paladin.application.encryption.symmetric;

/**
 * Created by ivan on 27.11.16.
 */
public interface SymmetricEncryption {
//    void setKey(String myKey);

    String encrypt(String stringToEncrypt, String sessionKey);

    String decrypt(String stringToDecrypt, String sessionKey);
}
