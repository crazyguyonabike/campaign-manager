package com.example.campaign.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.campaign.domain.Place;

@Service
public class GooglePlaceService implements PlaceService {

    @Value("${google.api.key}")
    private String apiKey;

    public Place getPlace(String key) {
        return null;
    }
}
