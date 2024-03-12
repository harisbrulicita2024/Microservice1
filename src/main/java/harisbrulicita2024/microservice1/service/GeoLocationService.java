package harisbrulicita2024.microservice1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class GeoLocationService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CompletableFuture<String> getCityFromIp(String ip) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        return client
                .prepareGet("https://ip-geo-location.p.rapidapi.com/ip/check?format=json&ip=" + ip)
                .setHeader("X-RapidAPI-Key", "040b8496b6mshc4400bcae5b3d5cp1ab879jsn5055542b358d")
                .setHeader("X-RapidAPI-Host", "ip-geo-location.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response.getResponseBody());
                        // Use the correct JSON Pointer
                        return jsonNode.at("/city/name").asText();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .whenComplete((result, throwable) -> {
                    try {
                        client.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
