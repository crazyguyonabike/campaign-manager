package com.example.campaign.domain;

import org.joda.time.Instant;

import com.javadocmd.simplelatlng.LatLng;

public class TimestampedLocation { 
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
        return timestamp;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public LatLng getLocation() {
        return location;
    }
}
            
    