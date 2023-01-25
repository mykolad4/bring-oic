package org.example.bring.model;

import org.example.bring.annotation.Bean;

@Bean("httpSender")
public class HttpSenderService implements SenderService {
    @Override
    public String sendMessage(String message) {
        System.out.printf("Sending \"%s\" via http", message);
        return "http " + message;
    }
}
