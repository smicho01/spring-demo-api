package org.severinu.demoapi.api.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public SnsClient snsClient() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    @Bean
    public SqsClient sqsClient() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

//    @Bean
//    public S3Client s3Client() {
//        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
//        return S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
//                .build();
//    }

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3ClientBuilder amazonS3ClientBuilder = AmazonS3ClientBuilder.standard()
                .enablePathStyleAccess()
                .disableChunkedEncoding();

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3ClientBuilder.withCredentials(new AWSStaticCredentialsProvider(awsCredentials));

        return amazonS3ClientBuilder.build();
    }

    @Bean
    public TransferManager transferManager(AmazonS3 amazonS3) {
        return TransferManagerBuilder.standard().withS3Client(amazonS3).build();

    }

}
