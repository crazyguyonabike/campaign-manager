package com.example.campaign.service;

import java.io.StringReader;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.example.campaign.domain.Place;

@Service
public class GooglePlaceService implements PlaceService, InitializingBean, DisposableBean {
    private static Logger logger = Logger.getLogger(GooglePlaceService.class);
    private static String GOOGLE_PLACE_URL = "https://maps.googleapis.com/maps/api/place/details/xml?placeid=%s&key=%s";

    @Value("${google.api.key}")
    private String apiKey;

    private Client client;
    private DocumentBuilder documentBuilder;
    XPathExpression xPathExpressionStatus;
    XPathExpression xPathExpressionName;
    XPathExpression xPathExpressionAddress;
    XPathExpression xPathExpressionPhone;

    public void afterPropertiesSet() {
        client = ClientBuilder.newClient();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xPath = xPathfactory.newXPath();
            xPathExpressionStatus = xPath.compile("/PlaceDetailsResponse/status/text()");
            xPathExpressionName = xPath.compile("/PlaceDetailsResponse/result/name/text()");
            xPathExpressionAddress = xPath.compile("/PlaceDetailsResponse/result/formatted_address/text()");
            xPathExpressionPhone = xPath.compile("/PlaceDetailsResponse/result/formatted_phone_number/text()");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Place getPlace(String key) {
        Place place = null;
        String urlString = String.format(GOOGLE_PLACE_URL, key, apiKey);
        String entity = client.target(urlString).request().get(String.class);
        if (StringUtils.isNotEmpty(entity)) {
            try {
                Document document = documentBuilder.parse(new InputSource(new StringReader(entity)));
                if (xPathExpressionStatus.evaluate(document, XPathConstants.STRING).equals("OK")) {
                    place = new Place();
                    place.setName((String)xPathExpressionName.evaluate(document, XPathConstants.STRING));
                    place.setAddress((String)xPathExpressionAddress.evaluate(document, XPathConstants.STRING));
                    place.setPhone((String)xPathExpressionPhone.evaluate(document, XPathConstants.STRING));
                }
                documentBuilder.reset();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return place;
    }

    public void destroy() {
        client.close();
    }
}
