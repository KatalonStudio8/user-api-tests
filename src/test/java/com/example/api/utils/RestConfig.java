package com.example.api.utils;

import org.springframework.web.client.RestTemplate;

public class RestConfig {
    
    private static final String BASE_URL = "https://fakestoreapi.com";
    private static RestTemplate restTemplate;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }
}
