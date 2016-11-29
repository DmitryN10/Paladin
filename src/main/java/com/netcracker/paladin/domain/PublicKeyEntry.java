package com.netcracker.paladin.domain;

/**
 * Created by ivan on 27.11.16.
 */
public class PublicKeyEntry {
    private final String email;
    private final byte[] publicKey;

    public PublicKeyEntry(String email, byte[] publicKey) {
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
