package model;

import java.net.http.HttpClient;
import java.util.HashMap;

public class User {
    private String key;
    private String secret;
    private String baseUrl;
    public HashMap<String, String> limitOrders;
    public HashMap<String, String> ordersArchive;
    private HttpClient client;

    public User(String key, String secret, String baseUrl) {
        this.key = key;
        this.secret = secret;
        this.baseUrl = baseUrl;
        limitOrders = new HashMap<>();
        ordersArchive = new HashMap<>();
        client = HttpClient.newHttpClient();
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpClient getClient() {
        return client;
    }
}