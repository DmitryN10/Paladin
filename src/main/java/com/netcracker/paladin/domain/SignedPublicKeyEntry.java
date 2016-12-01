package com.netcracker.paladin.domain;

/**
 * Created by ivan on 02.12.16.
 */
public class SignedPublicKeyEntry extends PublicKeyEntry {
    private final byte[] signature;

    public SignedPublicKeyEntry(String email, byte[] publicKey, byte[] signature) {
        super(email, publicKey);
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }
}
