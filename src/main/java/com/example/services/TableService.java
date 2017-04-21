package com.example.services;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.example.model.Person;
import com.example.repository.CrudOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by shailendra.singh on 4/20/17.
 */
@Component
public class TableService implements CrudOperation{

    public static final String TABLE_NAME = "person-test-table";

    private final Logger logger = LoggerFactory.getLogger(TableService.class);

    @Autowired
    private DynamoDbBuilder dynamoDbBuilder;

    private DynamoDBMapper dynamoDBMapper;

    public void initTableService() throws InterruptedException {

        DynamoDB dynamoDB = new DynamoDB(dynamoDbBuilder.getAmazonDynamoDBClient());
        /**
         * dynamoDBMapper IS required for hla, made from dynamodbclient
         */
        dynamoDBMapper = new DynamoDBMapper(dynamoDbBuilder.getAmazonDynamoDBClient());

        // create createtablerequest
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Person.class);

        // add provision throughput, read,write
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L,5L));

        // create table with this config
        Table table = dynamoDB.createTable(tableRequest);

        // print table description
        describeTable(table.waitForActive());

        // init crud ops
        initCrudOps();
    }


    @Override
    public void add(Person person) throws Exception {
        dynamoDBMapper.save(person);
    }

    @Override
    public void remove(String uuid) throws Exception {

    }

    private void describeTable(TableDescription tableDescription) {
        System.out.println("TABLE-DESCRIPTION: " + tableDescription.toString());
    }

    private void initCrudOps() {

        Person newPerson  = new Person();
        newPerson.setFirstName("firstName");
        newPerson.setLastName("lastname");
        newPerson.setAge(23);

        try {
            add(newPerson);
        } catch (Exception e) {
            logger.error("Failed to add person:" + e.getMessage());
        }
    }
}
