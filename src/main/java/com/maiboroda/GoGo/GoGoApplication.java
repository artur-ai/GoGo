package com.maiboroda.GoGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
public class GoGoApplication {
public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ApplicationContext context = SpringApplication.run(GoGoApplication.class, args);
    }
}