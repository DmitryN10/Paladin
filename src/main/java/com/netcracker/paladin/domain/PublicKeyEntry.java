package com.netcracker.paladin.domain;

/**
 * Created by ivan on 27.11.16.
 */
public class PublicKeyEntry {
    private String email;
    private String publicKey;

    public PublicKeyEntry(String email, String publicKey) {
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
