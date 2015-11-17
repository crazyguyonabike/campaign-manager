package com.example.campaign.service;

import com.example.campaign.domain.Place;

public interface PlaceMessageTransportService {
    public void sendPlaceMessage(String messageTransport, Place place);
}
