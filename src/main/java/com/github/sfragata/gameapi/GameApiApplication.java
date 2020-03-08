package com.github.sfragata.gameapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start SpringBoot application
 * @author Silvio Fragata
 *
 */
@SpringBootApplication
public class GameApiApplication {

    public static void main(
        final String[] args) {

        SpringApplication.run(GameApiApplication.class, args);
    }

}
