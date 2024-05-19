package com.journal.newmongoproject.service;

import com.journal.newmongoproject.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private static final String apiKey = "YWJjX3JhemFfaGFzc2FuOlF1Nkw1b01hOTM=";
    private static final String API = "https://api.meteomatics.com/2024-05-11T00:00:00Z--2024-05-14T00:00:00Z:PT1H/t_2m:C/52.520551,13.461804/json";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeatherData(){
        // API.replace("CITY", city).replace("API_KEY", apiKey);

        // Create HttpHeaders object and set your headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + apiKey);
        // Add other headers as needed

        // Create HttpEntity with headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<WeatherResponse> response =  restTemplate.exchange(API, HttpMethod.GET, entity, WeatherResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
             System.out.println("Response: " + response.getBody());
        }else{
            System.out.println("Error: " + response.getStatusCode());
        }
        return response.getBody();
    }

}
