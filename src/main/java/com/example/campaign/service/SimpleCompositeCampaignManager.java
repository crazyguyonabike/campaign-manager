package com.example.campaign.service;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.campaign.domain.Identified;
import com.example.campaign.repository.IdentifiedRepository;

@Service
public class SimpleCompositeCampaignManager implements CampaignManager {
    private static Logger logger = Logger.getLogger(SimpleCompositeCampaignManager.class);

    @Autowired
    public IdentifiedRepository identifiedRepository;

    @Async
    public void manage(String id, String location) {
        logger.debug(String.format("managing id: %s with location %s", id, location));
        Identified identified = identifiedRepository.findByIdentifier(id);
        logger.debug(String.format("found identified[%d] %s with message transport %s", identified.getId(), identified.getIdentifier(), identified.getMessageTransport()));
    }
}
