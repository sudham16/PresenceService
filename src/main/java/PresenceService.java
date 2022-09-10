import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PresenceService {
    OauthClient OauthClient = new OauthClient();

    public static void main(String[] args) throws OAuthProblemException, OAuthSystemException, ExecutionException, InterruptedException, IOException {
        PresenceService ps = new PresenceService();
        String token= ps.OauthClient.getOauthToken();
        WebSocketService webSocketService =  new WebSocketService("wss://isf-notification-perf.test.gaiacloud.jpmchase.net:443/praestosf/events/websocket?access_token="+token);
        webSocketService.getClientSession().sendMessage(new TextMessage("{\"type\":\"SUBSCRIBE\"}"));
        while (!StringUtils.hasText(webSocketService.getResponse())){

        }
    }
}
