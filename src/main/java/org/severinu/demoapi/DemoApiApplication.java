package org.severinu.demoapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemoApiApplication {

    public static void main(String[] args) {
        System.out.println("Demo API Maven app works fine!");
        log.info("Demo API Maven app works fine!");
        SpringApplication.run(DemoApiApplication.class, args);
    }

}
