package com.example.campaign.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import org.apache.log4j.Logger;

import com.example.campaign.domain.Place;

@Service
public class TwilioPlaceMessageTransportService implements PlaceMessageTransportService, InitializingBean, DisposableBean {
    private static Logger logger = Logger.getLogger(TwilioPlaceMessageTransportService.class);
    private static String TWILIO_URL = "https://api.twilio.com/2010-04-01/Accounts/%s/SMS/Messages.xml";
    private static String DEFAULT_BODY = "HEY! There is a %s about %f miles from you. Located at %s with phone %s";

    @Value("${sms.twilio.account.sid}")
    private String sid;

    @Value("${sms.twilio.auth.token}")
    private String token;

    @Value("${sms.twilio.number}")
    private String smsNumber;

    private Client client;

    public void afterPropertiesSet() {
        client = ClientBuilder.newClient();
        HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature.basic(sid, token);
        client.register(httpAuthenticationFeature);
    }

    public void sendPlaceMessage(String messageTransport, Place place) {
        Form form = new Form();
        form.param("From", smsNumber);
        form.param("To", messageTransport);
        form.param("Body", String.format(DEFAULT_BODY, place.getName(), place.getDistance(), place.getAddress(), place.getPhone()));
        client.target(String.format(TWILIO_URL, sid)).request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    }

    public void destroy() {
        client.close();
    }
}
