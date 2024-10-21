package org.severinu.demoapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.severinu.demoapi.beanconfigurationfiddle.Car;
import org.severinu.demoapi.beanconfigurationfiddle.CarsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@Slf4j
public class DemoApiApplication {

    public static void main(String[] args) {
        log.info("Demo API Maven app works fine!");
        SpringApplication.run(DemoApiApplication.class, args);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CarsConfig.class);

        Car car = ctx.getBean(Car.class);
        car.start();
    }

}
