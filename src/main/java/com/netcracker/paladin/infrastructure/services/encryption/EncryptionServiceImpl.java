package com.netcracker.paladin.infrastructure.services.encryption;

import com.netcracker.paladin.infrastructure.services.encryption.asymmetric.AsymmetricEncryption;
import com.netcracker.paladin.infrastructure.services.encryption.exceptions.NoPrivateKeyException;
import com.netcracker.paladin.infrastructure.services.encryption.sessionkeygen.SessionKeygen;
import com.netcracker.paladin.infrastructure.services.encryption.symmetric.SymmetricEncryption;
import com.netcracker.paladin.domain.PublicKeyEntry;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;
import com.netcracker.paladin.swing.exceptions.NoPublicKeyForEmailException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * Created by ivan on 27.11.16.
 */
public class EncryptionServiceImpl implements EncryptionService {
    private final PublicKeyEntryRepository publicKeyEntryRepository;
    private final AsymmetricEncryption asymmetricEncryption;
    private final SymmetricEncryption symmetricEncryption;
    private final SessionKeygen sessionKeygen;

    private byte[] privateKey;

    public EncryptionServiceImpl(
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
    public byte[] encryptEmail(String plainText, String recipient){
        if(privateKey == null){
            throw new NoPrivateKeyException();
        }
        try {
            Key sessionKey = sessionKeygen.generateKey();
            byte[] cipherText = symmetricEncryption.encrypt(plainText.getBytes("UTF-8"), sessionKey.getEncoded());
            byte[] encryptedSessionKey = asymmetricEncryption.encrypt(sessionKey.getEncoded(), findPublicKey(recipient));
            return ArrayUtils.addAll(encryptedSessionKey, cipherText);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String decryptEmail(byte[] cipherTextAndEncryptedSessionKey){
        if(privateKey == null){
            throw new NoPrivateKeyException();
        }
        try {
            byte[] encryptedSessionKey = ArrayUtils.subarray(cipherTextAndEncryptedSessionKey, 0, 128);
            byte[] cipherText = ArrayUtils.subarray(cipherTextAndEncryptedSessionKey, 128, cipherTextAndEncryptedSessionKey.length);

            byte[] sessionKey = asymmetricEncryption.decrypt(encryptedSessionKey, privateKey);
            byte[] plainText = symmetricEncryption.decrypt(cipherText, sessionKey);
            return new String(plainText, "UTF-8");
        } catch (NoPublicKeyForEmailException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] generatePrivateKey(){
        this.privateKey = asymmetricEncryption.generatePrivateKey();
        return privateKey;
    }

    @Override
    public void setPrivateKey(byte[] privateKey){
        this.privateKey = privateKey;
    }

    @Override
    public byte[] getOwnPublicKey(){
        if(privateKey == null){
            throw new NoPrivateKeyException();
        }
        return asymmetricEncryption.generatePublicKey(privateKey);
    }

    @Override
    public void addPublicKey(String email, byte[] publicKey){
        publicKeyEntryRepository.insert(new PublicKeyEntry(email, publicKey));
    }

    private byte[] findPublicKey(String email){
        return publicKeyEntryRepository.findByEmail(email).getPublicKey();
    }
}
