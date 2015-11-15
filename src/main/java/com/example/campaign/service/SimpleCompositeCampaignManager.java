package com.example.campaign.service;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class SimpleCompositeCampaignManager implements CampaignManager {
    private static Logger logger = Logger.getLogger(SimpleCompositeCampaignManager.class);

    @Async
    public void manage(String id, String location) {
        logger.debug(String.format("managing id: %s with location %s", id, location));
    }
}
