package org.example.bring.model;

import org.example.bring.annotation.Bean;

@Bean("messageService")
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "Hello";
    }
}
