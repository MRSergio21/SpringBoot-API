package org.rest.teamapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamApiApplication.class, args);

        System.out.println("Team API is running... ");
    }
}
