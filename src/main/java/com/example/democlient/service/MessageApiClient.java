package com.example.democlient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MessageApiClient {

    private final WebClient webClient; // configured with OAuth2

    public String fetchMessages() {
        return webClient
                .get()
                .uri("http://localhost:8082/api/messages")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

