package com.example.repository;

import com.example.model.Person;

/**
 * Created by shailendra.singh on 4/21/17.
 */
public interface CrudOperation {

    void add (Person person) throws Exception;

    void remove (String uuid) throws Exception;
}
