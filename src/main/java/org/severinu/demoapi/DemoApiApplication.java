package org.severinu.demoapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.severinu.demoapi.beanconfigurationfiddle.Car;
import org.severinu.demoapi.beanconfigurationfiddle.CarsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.paginators.ListTopicsIterable;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class DemoApiApplication {

    public static void main(String[] args) {
        log.info("Demo API Maven app works fine!");
        SpringApplication.run(DemoApiApplication.class, args);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CarsConfig.class);
//        Car car = ctx.getBean(Car.class);
//        car.start();

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create("",
                "");
        SnsClient snsClient = SnsClient.builder()
                .region(Region.of("eu-west-2"))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        listSNSTopics(snsClient);
        snsClient.close();
    }

    public static void listSNSTopics(SnsClient snsClient) {
        try {
            ListTopicsIterable listTopics = snsClient.listTopicsPaginator();
            listTopics.stream()
                    .flatMap(r -> r.topics().stream())
                    .forEach(content -> System.out.println(" Topic ARN: " + content.topicArn()));

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

}
