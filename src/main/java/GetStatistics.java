import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.UUID;


public class GetStatistics {
   /* public  static void main(String args[]) throws IOException, URISyntaxException {
        gatherStats("token","subscriberId");
    }*/

    public static void gatherStats(String token,String subscriptionId)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = GetStatistics.class.getResourceAsStream("queues.json");
            JsonNode jsonNode = mapper.readValue(is, JsonNode.class);
            String jsonString = mapper.writeValueAsString(jsonNode);
            jsonString = jsonString.replace("#nid#", subscriptionId);
            System.out.println(jsonString);
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI("https://isf-presence-perf.test.gaiacloud.jpmchase.net/isf/presence/supervisor/queues/subscriptions");
            //URI uri = new URI("http://localhost:8080/greeting/post");
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+ token);
            headers.set("Trace-Id", UUID.randomUUID().toString());
            headers.set("Content-Type", "application/json");
            //headers.set("Accept","application/json");
            headers.set("Accept", "application/vnd.jpmc.isf.external-config-service.supervisorQueuesSubscription.v1+json,application.vnd.jpmc.isf.error.v1+json");
            HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
            System.out.println(result);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
