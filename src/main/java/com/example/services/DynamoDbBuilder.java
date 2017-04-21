package com.example.services;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by shailendra.singh on 4/20/17.
 */
@Component
public class DynamoDbBuilder {

    private AmazonDynamoDB amazonDynamoDBClient;

    @Value("${cloud.aws.region.static}")
    private String awsAccountRegion;

    @PostConstruct
    public void dynamoDbBuilder() {
        amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.fromName(awsAccountRegion))
                .build();
    }

    public AmazonDynamoDB getAmazonDynamoDBClient() {
        return amazonDynamoDBClient;
    }
}
