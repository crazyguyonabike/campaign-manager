package com.example.campaign.service;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import com.javadocmd.simplelatlng.LatLng;

import com.example.campaign.domain.Identified;
import com.example.campaign.repository.IdentifiedRepository;
import com.example.campaign.service.IdentifiedService;

@Service
public class SimpleCompositeCampaignManager implements CampaignManager {
    private static Logger logger = Logger.getLogger(SimpleCompositeCampaignManager.class);

    @Autowired
    private IdentifiedRepository identifiedRepository;

    @Autowired
    private IdentifiedService identifiedService;

    @Async
    public void manage(String id, String location) {
        logger.debug(String.format("managing id: %s with location %s", id, location));
        Identified identified = identifiedRepository.findByIdentifier(id);
        logger.debug(String.format("found identified[%d] %s with message transport %s", identified.getId(), identified.getIdentifier(), identified.getMessageTransport()));
        String [] parts = location.split(",");
        LatLng latlng = new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
        Double multiplier = identifiedService.handleLocatedIdentified(identified, latlng);

    }
}
