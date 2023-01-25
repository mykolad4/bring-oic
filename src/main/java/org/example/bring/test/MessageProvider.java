package org.example.bring.test;

import org.example.bring.annotation.Bean;

@Bean
public class MessageProvider {
    public String provideMessage() {
        return "new message";
    }
}
