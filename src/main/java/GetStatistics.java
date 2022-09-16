import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class GetStatistics {
  /*  public static void main(String[] args) throws IOException, URISyntaxException {
        // System.out.println();
        Results.getResults(gatherStats("token", "subscriberId"));
    }*/

    public static String gatherStats(String token, String subscriptionId) throws IOException, URISyntaxException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = GetStatistics.class.getResourceAsStream("queues.json");
        JsonNode jsonNode = mapper.readValue(is, JsonNode.class);
        String jsonString = mapper.writeValueAsString(jsonNode);
        jsonString = jsonString.replace("#nid#", subscriptionId);
        System.out.println(jsonString);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        URI uri = new URI("https://isf-presence-perf.test.gaiacloud.jpmchase.net/isf/presence/supervisor/queues/subscriptions");
        //URI uri = new URI("http://localhost:8080/greeting/post");
        HttpHeaders headers = Util.getHttpHeaders(token);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        return result.getBody();
    }
    public static void unSubscribe(String subscriptionId)   {
        Map< String, String > params = new HashMap< String, String >();
        params.put("subscriptionID", subscriptionId);
        HttpHeaders headers = Util.getHttpHeaders("");
        String url ="https://praesto-isf-presence-service.aps.test.na-6y.gap.jpmchase.net/isf/presence/supervisor/agent/subscriptions/{subscriptionID}";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url,params);
    }


}
