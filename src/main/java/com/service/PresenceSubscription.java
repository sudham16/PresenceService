package com.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class PresenceSubscription {

    @Value("${subscription.url}")
    private String subscriptionUrl;

    @Value("${unSubscribe.url}")
    private String unSubscribeUrl;


    private static final Logger LOGGER= LoggerFactory.getLogger(PresenceSubscription.class);


    public String getSubscription(String token, String subscriptionId) throws IOException, URISyntaxException {

        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource("queues.json");
        FileInputStream is = new FileInputStream(resource.getFile());
        JsonNode jsonNode = mapper.readValue(is, JsonNode.class);
        String jsonString = mapper.writeValueAsString(jsonNode);
        jsonString = jsonString.replace("#nid#", subscriptionId);
        LOGGER.debug(jsonString);
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        URI uri = new URI(subscriptionUrl);
        HttpHeaders headers = Util.getHttpHeaders(token);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        return result.getBody();
    }

    public void unSubscribe(String subscriptionId) {
        Map<String, String> params = new HashMap<>();
        params.put("subscriptionID", subscriptionId);
        HttpHeaders headers = Util.getHttpHeaders("");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(unSubscribeUrl, params);
    }


}
