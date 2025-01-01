package com.example.s3.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockS3Config {

    @Bean
    public AmazonS3Client mockAmazonS3Client() {
        return Mockito.mock(AmazonS3Client.class);
    }
}