package com.netcracker.paladin.domain;

/**
 * Created by ivan on 27.11.16.
 */
public class PublicKeyEntry {
    private String email;
    private byte[] publicKey;

    public PublicKeyEntry(String email, byte[] publicKey) {
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getOwnPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
}
