package com.service;


import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.Map;

@Service
public class PresenceService {

    @Autowired
    private OauthClient OauthClient;
    @Autowired
    private PresenceSubscription presenceStatistics;
    @Autowired
    private ContentFileReader contentFileReader;
    private  WebSocketService webSocketService;
    private static final Logger LOGGER= LoggerFactory.getLogger(PresenceService.class);
    @Value("${websocket.url}")
    private String websocketUrl;

    @Value("${queue.output.directory}")
    private String outputPath;
    public String getQueueContentFromFile() throws Exception {
        return contentFileReader.returnFileContent();
    }

    public String refreshQueueContent() throws Exception {
        getQueueContent();
        return "Success";
    }

    public void getQueueContent() throws Exception {
        int timeInMS = 1000;
        int maxTimeInMS = 5000;
        String token = OauthClient.getOauthToken("OauthTokenCacheType");
        LOGGER.info("Token is " + token);
        WebSocketService webSocketService = new WebSocketService(websocketUrl + token, outputPath);
        webSocketService.sendMessage("{\"type\":\"SUBSCRIBE\"}");
        while (webSocketService.getResponse() == null) {
            Thread.sleep(timeInMS);
            if (timeInMS == maxTimeInMS) {
                LOGGER.error("WebSocket Response not received within expected time limit");
                throw new Exception("WebSocket Response not received within expected time limit");
            }
            timeInMS = timeInMS + 1000;
        }
        Gson gson = new Gson();
        Map<String, String> responseMap = gson.fromJson(webSocketService.getResponse(), Map.class);
        String subId = responseMap.get("id");
        String subscribed = responseMap.get("type");
        if (subscribed.equalsIgnoreCase("SUBSCRIBED")) {
            presenceStatistics.getSubscription("SubscriptionCacheType", token, subId);
            // presenceStatistics.unSubscribe(subId);
        } else {
            LOGGER.error("Subscription Not found");
        }
        webSocketService.closeClientSession();

    }
}
