package com.example.campaign.domain;

import javax.persistence.Entity;

@Entity
public class Identified extends DatabaseObject {
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
}
