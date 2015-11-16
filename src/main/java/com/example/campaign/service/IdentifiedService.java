package com.example.campaign.service;

import com.example.campaign.domain.Identified;

import com.javadocmd.simplelatlng.LatLng;

public interface IdentifiedService {
    public Double handleLocatedIdentified(Identified identified, LatLng location);
}
