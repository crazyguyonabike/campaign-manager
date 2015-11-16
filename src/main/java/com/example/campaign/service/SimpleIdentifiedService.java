package com.example.campaign.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.jcs.access.CacheAccess;

import com.example.campaign.domain.Identified;

import com.javadocmd.simplelatlng.LatLng;

import org.apache.log4j.Logger;

import com.example.campaign.domain.TimestampedLocation;

@Service
public class SimpleIdentifiedService implements IdentifiedService, DisposableBean {
    private static Logger logger = Logger.getLogger(SimpleIdentifiedService.class);

    @Autowired
    private CacheAccess<Identified, TimestampedLocation> identifiedCache;

    public void destroy() {
        identifiedCache.dispose();
    }

    public Double handleLocatedIdentified(Identified identified, LatLng location) {
        Double multiplier = 1.0;
        TimestampedLocation timestampedLocation = identifiedCache.get(identified);
        if (timestampedLocation != null) {
            // we can calculate a multiplier based on the location we found, the time between
            identifiedCache.remove(identified);
        } else
            identifiedCache.put(identified, new TimestampedLocation(location));
        return multiplier;
    }
}
