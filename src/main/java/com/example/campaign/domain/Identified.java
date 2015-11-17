package com.example.campaign.domain;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class Identified extends DatabaseObject implements Serializable {
    public String identifier;
    public String messageTransport;

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setMessageTransport(String messageTransport) {
        this.messageTransport = messageTransport;
    }

    public String getMessageTransport() {
        return this.messageTransport;
    }

    @Override
    public boolean equals(Object that) {
        boolean result = false;
        if (that instanceof Identified) {
            result = ((Identified)that).getIdentifier().equals(this.identifier);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
