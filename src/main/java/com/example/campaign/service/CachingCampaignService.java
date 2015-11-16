package com.example.campaign.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.jcs.access.GroupCacheAccess;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.BufferedReader;
import java.io.FileReader;

import com.javadocmd.simplelatlng.LatLng;

@Service
public class CachingCampaignService implements CampaignService, InitializingBean, DisposableBean {
    private static Logger logger = Logger.getLogger(CachingCampaignService.class);

    @Autowired
    private GroupCacheAccess<String, LatLng> placeCache;

    @Value("${campaign.directory}")
    private String campaignDirectory;

    public void afterPropertiesSet() {
        initializePlaceCache(placeCache);
    }

    public void destroy() {
        placeCache.dispose();
    }

    private void initializePlaceCache(GroupCacheAccess<String, LatLng> placeCache) {
        try {
            File [] files = new File(campaignDirectory).listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".campaign");
                    }
                });
            for (File file : files) {
                logger.debug(file.toString());
                String groupName = file.getName().split("\\.")[0];
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (StringUtils.isNotEmpty(line)) {
                    logger.debug(line);
                    String [] parts = line.split(",");
                    placeCache.putInGroup(parts[2], groupName, new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1])));
                    line = reader.readLine();
                }
                reader.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }            
    }
}    
