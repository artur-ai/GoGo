package com.maiboroda.GoGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class GoGoApplication {


    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.setProperty("DB_USERNAME", "postgres");
        System.setProperty("DB_PASSWORD", "postgres");

        SpringApplication.run(GoGoApplication.class, args);
    }
}