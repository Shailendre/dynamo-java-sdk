package com.example;

import com.example.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class DynamoDBtestApplication {

    @Autowired
    private TableService tableService;

    public static void main(String[] args) {
        SpringApplication.run(DynamoDBtestApplication.class, args);
    }

    @PostConstruct
    public void initDB() {
        try {
            tableService.initTableService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}