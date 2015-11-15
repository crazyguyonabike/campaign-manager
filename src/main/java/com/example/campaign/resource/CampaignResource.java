package com.example.campaign.resource;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

import javax.inject.Inject;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.campaign.service.CampaignManager;

//import com.example.campaign.service.ShoppingListProcessorService;
//import com.example.campaign.repository.ShoppingListRepository;
//import com.example.campaign.domain.ShoppingList;

@Path("/")
public class CampaignResource {
    static final Logger logger = Logger.getLogger(CampaignResource.class);

    @Inject
    private CampaignManager campaignManager;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getData(@QueryParam("id") String id, @QueryParam("location") String location) throws Exception {
        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.BAD_REQUEST);
        logger.debug(String.format("found id: %s, and location %s", id, location));
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(location)) {
            campaignManager.manage(id, location);
            responseBuilder = Response.noContent();
        }
        return responseBuilder.build();
    }
}
