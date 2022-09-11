import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


public class GetStatistics {
   /* public  static void main(String args[]) throws IOException, URISyntaxException {
        GetStatistics getStatistics = new GetStatistics();
        extracted("token","subscriberId");
    }*/

    public static void gatherStats(String token,String subscriptionId) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = GetStatistics.class.getResourceAsStream("queues.json");
        JsonNode jsonNode  = mapper.readValue(is, JsonNode.class);
        String jsonString = mapper.writeValueAsString(jsonNode);
        jsonString=jsonString.replace("#nid#",subscriptionId);
        System.out.println(jsonString);
        WebClient client = WebClient.create();
        String response = client.post()
               // .uri(new URI("https://isf-presence-perf.test.gaiacloud.jpmchase.net/isf/presence/supervisor/queues/subscriptions"))
                .uri(new URI("http://localhost:8080/greeting/post"))
                .header("Authorization", "Bearer "+token)
                .header("Pragma","no-cache")
                .header("X-Praesto-Client-Request-Id", UUID.randomUUID().toString())
                //.header("X-Praesto-Specialist-Id",)
                .header("Trace-Id",UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(jsonString), String.class))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
    }
}
