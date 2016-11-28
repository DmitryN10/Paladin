package com.netcracker.paladin.domain;

/**
 * Created by ivan on 26.11.16.
 */
public class MessageEntry {

    private String from;
    private String subject;
    private String text;
    private byte[] cipherBlob;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getCipherBlob() {
        return cipherBlob;
    }

    public void setCipherBlob(byte[] cipherBlob) {
        this.cipherBlob = cipherBlob;
    }
}
