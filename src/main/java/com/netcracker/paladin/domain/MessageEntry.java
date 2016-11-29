package com.netcracker.paladin.domain;

import java.util.Date;

/**
 * Created by ivan on 26.11.16.
 */
public class MessageEntry {

    private String from;
    private String subject;
    private String message;
    private Date sentDate;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public byte[] getCipherBlob() {
        return cipherBlob;
    }

    public void setCipherBlob(byte[] cipherBlob) {
        this.cipherBlob = cipherBlob;
    }
}
