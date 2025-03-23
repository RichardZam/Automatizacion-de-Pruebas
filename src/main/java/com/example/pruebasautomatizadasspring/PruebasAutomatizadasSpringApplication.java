package com.example.pruebasautomatizadasspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PruebasAutomatizadasSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(PruebasAutomatizadasSpringApplication.class, args);
    }
}
