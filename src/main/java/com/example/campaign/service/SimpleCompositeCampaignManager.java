package com.example.campaign.service;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import com.javadocmd.simplelatlng.LatLng;

import com.example.campaign.domain.Identified;
import com.example.campaign.domain.Place;
import com.example.campaign.repository.IdentifiedRepository;
import com.example.campaign.service.IdentifiedService;

@Service
public class SimpleCompositeCampaignManager implements CampaignManager {
    private static Logger logger = Logger.getLogger(SimpleCompositeCampaignManager.class);

    @Autowired
    private IdentifiedRepository identifiedRepository;

    @Autowired
    private IdentifiedService identifiedService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private PlaceMessageTransportService placeMessageTransportService;

    @Async
    public void manage(String id, String location) {
        Identified identified = identifiedRepository.findByIdentifier(id);
        if (identified != null) {
            String [] parts = location.split(",");
            LatLng latlng = null;
            try {
                latlng = new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            if (latlng != null) {
                Double multiplier = identifiedService.handleLocatedIdentified(identified, latlng);
                Place place = campaignService.getNearestPlace(latlng, multiplier);
                if (place != null) {
                    placeMessageTransportService.sendPlaceMessage(identified.getMessageTransport(), place);
                }
            }
        }
    }
}
