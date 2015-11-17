package com.example.campaign.domain;

import org.joda.time.Instant;

import com.javadocmd.simplelatlng.LatLng;

import java.io.Serializable;

public class TimestampedLocation implements Serializable {
    private LatLng location;
    private Instant timestamp;

    public TimestampedLocation(LatLng location) {
        timestamp = new Instant();
        this.location = location;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public LatLng getLocation() {
        return this.location;
    }
}
