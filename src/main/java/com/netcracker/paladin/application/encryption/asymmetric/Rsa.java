package com.netcracker.paladin.application.encryption.asymmetric;

/**
 * Created by ivan on 27.11.16.
 */

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class Rsa implements AsymmetricEncryption {

    private final String ALGORITHM = "RSA";

    @Override
    public KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            return keyGen.generateKeyPair();
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
    public String encrypt(String text, PublicKey key) {
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String decrypt(String text, PrivateKey key) {
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
