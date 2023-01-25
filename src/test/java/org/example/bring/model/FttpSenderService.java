package org.example.bring.model;

import org.example.bring.annotation.Bean;

@Bean("fttpSender")
public class FttpSenderService implements SenderService {
    @Override
    public String sendMessage(String message) {
        System.out.printf("Sending \"%s\" via fttp", message);
        return "fttp " + message;
    }
}
