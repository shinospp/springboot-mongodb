package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.collection.Example;

import java.util.List;

public interface ExampleRepository extends MongoRepository<Example, String> {
    // Custom query method using name
    List<Example> findByName(String name);
}

