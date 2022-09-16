import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import java.util.Map;

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
            String output = GetStatistics.gatherStats(token,subId);
            Results.getResults(output);
            GetStatistics.unSubscribe(subId);
        }
        Thread.sleep(5000);
        webSocketService.getClientSession().close();

    }
}
