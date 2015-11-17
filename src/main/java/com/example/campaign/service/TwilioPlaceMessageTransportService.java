package com.example.campaign.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.campaign.domain.Place;

@Service
public class TwilioPlaceMessageTransportService {

    @Value("${sms.twilio.account.sid}")
    private String sid;

    @Value("${sms.twilio.auth.token}")
    private String token;

    @Value("${sms.twilio.url}")
    private String urlString;

    @Value("${sms.twilio.number}")
    private String smsNumber;

    public void sendPlaceMessage(String messageTransport, Place place) {
        return;
    }
}
