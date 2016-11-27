package com.netcracker.paladin.domain;

import java.security.PublicKey;

/**
 * Created by ivan on 27.11.16.
 */
public class PublicKeyEntry {
    private String email;
    private PublicKey publicKey;

    public PublicKeyEntry(String email, PublicKey publicKey) {
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
