package com.example.campaign.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.jcs.access.CacheAccess;

import com.example.campaign.domain.Identified;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import org.joda.time.Instant;
import org.joda.time.Duration;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.BooleanUtils;

import com.example.campaign.domain.TimestampedLocation;

@Service
public class SimpleIdentifiedService implements IdentifiedService, DisposableBean {
    private static Logger logger = Logger.getLogger(SimpleIdentifiedService.class);

    @Value("${device.skew:false}")
    private String deviceSkew;

    @Autowired
    private CacheAccess<Identified, TimestampedLocation> identifiedCache;

    public void destroy() {
        identifiedCache.dispose();
    }

    public Double handleLocatedIdentified(Identified identified, LatLng location) {
        Double multiplier = 1.0;
        TimestampedLocation timestampedLocation = identifiedCache.get(identified);
        if (timestampedLocation != null) {
            // we can calculate a multiplier based on the location we found, the time elapsed
            Duration duration = new Duration(timestampedLocation.getTimestamp(), new Instant());
            Double distance = LatLngTool.distance(location, timestampedLocation.getLocation(), LengthUnit.MILE);
            int seconds = duration.toStandardSeconds().getSeconds();
            if (BooleanUtils.toBoolean(deviceSkew))
                seconds += 600; // plus 10 minutes
            logger.debug(String.format("traveled %f miles in %f minutes", distance, seconds/60.0));
            double milesPerHour = distance/seconds * 3600.0;
            logger.debug(String.format("seems to be traveling at %f miles/hour with bearing %f", milesPerHour, LatLngTool.initialBearing(location, timestampedLocation.getLocation())));
            identifiedCache.remove(identified);
        } else
            identifiedCache.put(identified, new TimestampedLocation(location));
        return multiplier;
    }
}
