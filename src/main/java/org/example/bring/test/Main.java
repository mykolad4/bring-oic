package org.example.bring.test;

import org.example.bring.context.ApplicationContext;
import org.example.bring.context.ApplicationContextImpl;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("org.example.bring.test");

        System.out.println("test");
    }
}
