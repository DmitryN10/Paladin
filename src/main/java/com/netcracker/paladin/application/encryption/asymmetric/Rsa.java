package com.netcracker.paladin.application.encryption.asymmetric;

/**
 * Created by ivan on 27.11.16.
 */

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa implements AsymmetricEncryption {

    private final String ALGORITHM = "RSA";
    private final int KEYSIZE = 1024;
    private final KeyFactory keyFactory;
    private final Cipher cipher;
    private final KeyPairGenerator keyPairGenerator;

    public Rsa() {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
            cipher = Cipher.getInstance(ALGORITHM);
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEYSIZE);
        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new IllegalStateException(e);
        }
    }

    @Override
    public KeyPair generateKeyPair() {
        try {
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public PublicKey generatePublicKey(PrivateKey privateKey){
        if(privateKey instanceof RSAPrivateCrtKeySpec == false){
            throw new IllegalStateException();
        }
        RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = (RSAPrivateCrtKeySpec) privateKey;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                rsaPrivateCrtKeySpec.getModulus(),
                rsaPrivateCrtKeySpec.getPublicExponent());
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] encrypt(byte[] sequenceToEncrypt, PublicKey publicKey) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(sequenceToEncrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] encrypt(byte[] sequenceToEncrypt, byte[] publicKeyBytes) {
        try {
            return encrypt(sequenceToEncrypt, keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] decrypt(byte[] sequenceToDecrypt, PrivateKey privateKey) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            System.out.println("Size of ecrypted session key: "+sequenceToDecrypt.length);
            return cipher.doFinal(sequenceToDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] decrypt(byte[] sequenceToDecrypt, byte[] privateKeyBytes) {
        try {
            return decrypt(sequenceToDecrypt, keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
