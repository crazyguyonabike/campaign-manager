package com.example.campaign.service;

import com.javadocmd.simplelatlng.LatLng;

import com.example.campaign.domain.Place;

public interface CampaignService {
    public Place getNearestPlace(LatLng location, Double multiplier);
}
    
    
