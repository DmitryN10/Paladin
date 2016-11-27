package com.netcracker.paladin.application.encryption;

import com.netcracker.paladin.application.encryption.asymmetric.AsymmetricEncryption;
import com.netcracker.paladin.application.encryption.sessionkeygen.SessionKeygen;
import com.netcracker.paladin.application.encryption.symmetric.SymmetricEncryption;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;

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
    }

    @Override
    public String encryptEmail(String plainText, String recipient){
        Key sessionKey = sessionKeygen.generateKey();
        String cipherText = symmetricEncryption.encrypt(plainText, sessionKey.toString());
        String encryptedSessionKey = new String(asymmetricEncryption.encrypt(sessionKey.toString(), getKeyPair().getPublic()));
        cipherText = encryptedSessionKey+cipherText;
        return cipherText;
    }

    @Override
    public String decryptEmail(String cipherText){
        return cipherText;
    }

//    public PublicKey generatePublicKey(){
//        return asymmetricEncryption.generatePublicKey();
//    }

    public KeyPair getKeyPair(){
        return asymmetricEncryption.generateKeyPair();
    }
}
