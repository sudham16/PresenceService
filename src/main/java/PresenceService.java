import com.google.gson.Gson;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PresenceService {
    OauthClient OauthClient = new OauthClient();

    public static void main(String[] args) throws Exception {
        int maxTimeInMS=5000;
        int timeInMS=1000;
        PresenceService ps = new PresenceService();
        String token= ps.OauthClient.getOauthToken();
        WebSocketService webSocketService =  new WebSocketService("wss://isf-notification-perf.test.gaiacloud.jpmchase.net:443/praestosf/events/websocket?access_token="+token);
        webSocketService.getClientSession().sendMessage(new TextMessage("{\"type\":\"SUBSCRIBE\"}"));
        while (webSocketService.getResponse() == null ){
            Thread.sleep(timeInMS);
            if(timeInMS == maxTimeInMS){
                webSocketService.getClientSession().close();
                throw new Exception("WebSocket Response not received within expected time limit");
            }
            timeInMS=timeInMS+1000;
        }
        Gson gson = new Gson();
        Map<String,String> responseMap = gson.fromJson(webSocketService.getResponse(), Map.class);
        String subId = responseMap.get("id");
        String subscribed = responseMap.get("type");
        if(subscribed.equalsIgnoreCase("SUBSCRIBED")){
            GetStatistics.gatherStats(token,subId);
        }
        webSocketService.getClientSession().close();

    }
}
