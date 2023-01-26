package org.example.bring.model;

import org.example.bring.annotation.Autowired;
import org.example.bring.annotation.Bean;

@Bean("httpSender")
public class HttpSenderService implements SenderService {

    @Autowired
    private MessageService messageService;

    @Override
    public String sendMessage() {
        String message = messageService.getMessage();
        System.out.printf("Sending \"%s\" via http", message);
        return "http " + message;
    }
}
