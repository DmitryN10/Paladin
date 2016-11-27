package com.netcracker.paladin.application.encryption.sessionkeygen;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by ivan on 27.11.16.
 */
public class ChebiKeyGen implements KeyGen {
    private final KeyGenerator keyGenerator;

    public ChebiKeyGen() {
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom());
        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Key generateKey(){
        return keyGenerator.generateKey();
    }
}
