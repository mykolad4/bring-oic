package org.example.bring.model;

import org.example.bring.annotation.Autowired;
import org.example.bring.annotation.Bean;

@Bean("fttpSender")
public class FttpSenderService implements SenderService {

    @Autowired
    MessageService messageService;
    @Override
    public String sendMessage() {
        String message = messageService.getMessage();

        System.out.printf("Sending \"%s\" via fttp", message);
        return "fttp " + message;
    }
}
