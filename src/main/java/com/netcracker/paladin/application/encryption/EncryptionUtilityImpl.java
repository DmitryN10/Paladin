package com.netcracker.paladin.application.encryption;

import com.netcracker.paladin.application.encryption.asymmetric.AsymmetricEncryption;
import com.netcracker.paladin.application.encryption.sessionkeygen.SessionKeygen;
import com.netcracker.paladin.application.encryption.symmetric.SymmetricEncryption;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;
import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyPair;

/**
 * Created by ivan on 27.11.16.
 */
public class EncryptionUtilityImpl implements EncryptionUtility {
    private final PublicKeyEntryRepository publicKeyEntryRepository;
    private final AsymmetricEncryption asymmetricEncryption;
    private final SymmetricEncryption symmetricEncryption;
    private final SessionKeygen sessionKeygen;

    private KeyPair keyPair;

    public EncryptionUtilityImpl(
            PublicKeyEntryRepository publicKeyEntryRepository,
            AsymmetricEncryption asymmetricEncryption,
            SymmetricEncryption symmetricEncryption,
            SessionKeygen sessionKeygen) {
        this.publicKeyEntryRepository = publicKeyEntryRepository;
        this.asymmetricEncryption = asymmetricEncryption;
        this.symmetricEncryption = symmetricEncryption;
        this.sessionKeygen = sessionKeygen;
        this.keyPair = asymmetricEncryption.generateKeyPair();
    }

    @Override
    public byte[] encryptEmail(String plainText, String recipient){
        try {
            Key sessionKey = sessionKeygen.generateKey();
            byte[] cipherText = symmetricEncryption.encrypt(plainText.getBytes("UTF-8"), sessionKey.getEncoded());
            byte[] encryptedSessionKey = asymmetricEncryption.encrypt(sessionKey.getEncoded(), getKeyPair().getPublic().getEncoded());
//            System.out.println("Text: "+cipherText.length);
//            System.out.println("Key: "+encryptedSessionKey.length);
            return ArrayUtils.addAll(encryptedSessionKey, cipherText);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String decryptEmail(byte[] cipherTextAndEncryptedSessionKey){
        try {
            byte[] encryptedSessionKey = ArrayUtils.subarray(cipherTextAndEncryptedSessionKey, 0, 128);
            byte[] cipherText = ArrayUtils.subarray(cipherTextAndEncryptedSessionKey, 128, cipherTextAndEncryptedSessionKey.length);

            byte[] sessionKey = asymmetricEncryption.decrypt(encryptedSessionKey, getKeyPair().getPrivate());
            byte[] plainText = symmetricEncryption.decrypt(cipherText, sessionKey);
            return new String(plainText, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

//    public PublicKey generatePublicKey(){
//        return asymmetricEncryption.generatePublicKey();
//    }

    @Override
    public KeyPair getKeyPair(){
        return keyPair;
    }
}
