package com.netcracker.paladin.application.encryption;

import com.netcracker.paladin.application.encryption.asymmetric.AsymmetricEncryption;
import com.netcracker.paladin.application.encryption.sessionkeygen.SessionKeygen;
import com.netcracker.paladin.application.encryption.symmetric.SymmetricEncryption;
import com.netcracker.paladin.infrastructure.repositories.PublicKeyEntryRepository;

/**
 * Created by ivan on 27.11.16.
 */
public class EncryptionUtilityImpl implements EncryptionUtility {
    private final PublicKeyEntryRepository publicKeyEntryRepository;
    private final AsymmetricEncryption asymmetricEncryption;
    private final SymmetricEncryption symmetricEncryption;
    private final SessionKeygen sessionKeygen;

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
        return plainText;
    }

    @Override
    public String decryptEmail(String cipherText){
        return cipherText;
    }
}
