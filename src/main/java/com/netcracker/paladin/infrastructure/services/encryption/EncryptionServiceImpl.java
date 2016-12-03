package com.netcracker.paladin.infrastructure.services.encryption;

import com.netcracker.paladin.domain.PublicKeyEntry;
import com.netcracker.paladin.domain.SignedPublicKeyEntry;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;
import com.netcracker.paladin.infrastructure.repositories.exceptions.NoPublicKeyForEmailException;
import com.netcracker.paladin.infrastructure.services.encryption.asymmetric.AsymmetricEncryption;
import com.netcracker.paladin.infrastructure.services.encryption.exceptions.NoPrivateKeyException;
import com.netcracker.paladin.infrastructure.services.encryption.sessionkeygen.SessionKeygen;
import com.netcracker.paladin.infrastructure.services.encryption.symmetric.SymmetricEncryption;
import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
            byte[] sessionKey = sessionKeygen.generateKey();
            byte[] cipherText = symmetricEncryption.encrypt(plainText.getBytes("UTF-8"), sessionKey);
            byte[] encryptedSessionKey = asymmetricEncryption.encrypt(sessionKey, findPublicKey(recipient));
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
    public void addPublicKey(PublicKeyEntry publicKeyEntry){
        publicKeyEntryRepository.insert(publicKeyEntry);
    }

    @Override
    public void addPublicKey(String email, byte[] publicKey){
        addPublicKey(new PublicKeyEntry(email, publicKey));
    }

    @Override
    public byte[] getPublicKey(String email){
        return publicKeyEntryRepository.findByEmail(email).getPublicKey();
    }

    @Override
    public void deletePublicKey(String email){
        publicKeyEntryRepository.deleteByEmail(email);
    }

    @Override
    public List<PublicKeyEntry> getAllPublicKeyEntries(){
        return publicKeyEntryRepository.findAll();
    }

    @Override
    public List<String> getAllEmailsWithPublicKey(){
        List<PublicKeyEntry> allPublicKeyEntries = getAllPublicKeyEntries();
        List<String> allEmailsWithPublicKey = new ArrayList<>(allPublicKeyEntries.size());
        for(PublicKeyEntry publicKeyEntry : allPublicKeyEntries){
            allEmailsWithPublicKey.add(publicKeyEntry.getEmail());
        }
        return allEmailsWithPublicKey;
    }

    @Override
    public byte[] getSignature(byte[] data){
        return asymmetricEncryption.createSignature(data, privateKey);
    }

    @Override
    public SignedPublicKeyEntry getSignedPublicKeyEntry(String email){
        byte[] publicKey = publicKeyEntryRepository.findByEmail(email).getPublicKey();
        byte[] emailBytes = email.getBytes();
        byte[] publicKeyWithEmail = ArrayUtils.addAll(publicKey, emailBytes);
        byte[] signature = asymmetricEncryption.createSignature(publicKeyWithEmail, privateKey);
        return new SignedPublicKeyEntry(null, signature, publicKeyWithEmail);
    }

    @Override
    public SignedPublicKeyEntry getSignedPublicKeyEntry(PublicKeyEntry publicKeyEntry){
        String email = publicKeyEntry.getEmail();
        byte[] publicKey = publicKeyEntry.getPublicKey();
        byte[] emailBytes = publicKeyEntry.getEmail().getBytes();
        byte[] publicKeyWithEmail = ArrayUtils.addAll(publicKey, emailBytes);
        byte[] signature = asymmetricEncryption.createSignature(publicKeyWithEmail, privateKey);
        return new SignedPublicKeyEntry(null, signature, publicKeyWithEmail);
    }

    @Override
    public PublicKeyEntry getPublicKeyEntry(SignedPublicKeyEntry signedPublicKeyEntry){
        try {
            byte[] publicKeyWithEmail = signedPublicKeyEntry.getPublicKeyWithEmail();
            byte[] publicKey = ArrayUtils.subarray(publicKeyWithEmail, 0, 256);
            String email = new String(ArrayUtils.subarray(publicKeyWithEmail, 256, publicKeyWithEmail.length), "UTF-8");
            return new PublicKeyEntry(email, publicKey);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean verifySignature(byte[] publicKey, byte[] signature, byte[] data){
        return asymmetricEncryption.verifySignature(data, signature, publicKey);
    }

    @Override
    public boolean verifySignature(String signerEmail, byte[] signature, byte[] data){
        byte[] publicKey = publicKeyEntryRepository.findByEmail(signerEmail).getPublicKey();
        return verifySignature(publicKey, signature, data);
    }

    @Override
    public boolean verifySignature(SignedPublicKeyEntry signedPublicKeyEntry){
        return verifySignature(
                signedPublicKeyEntry.getSignerEmail(),
                signedPublicKeyEntry.getSignature(),
                signedPublicKeyEntry.getPublicKeyWithEmail());
    }

    @Override
    public byte[] findPublicKey(String email){
        return publicKeyEntryRepository.findByEmail(email).getPublicKey();
    }
}
