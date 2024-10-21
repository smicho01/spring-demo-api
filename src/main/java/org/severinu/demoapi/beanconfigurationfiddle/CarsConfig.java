package org.severinu.demoapi.beanconfigurationfiddle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarsConfig {

    @Bean
    public Engine engine() {
        return new Engine("V8");
    }

    @Bean
    public Car car() {
        return new Car(engine());
    }
}
