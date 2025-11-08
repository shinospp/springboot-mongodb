package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Example;
import com.example.demo.repository.ExampleRepository;


import java.util.List;

@RestController
@RequestMapping("/mongodb-app")
public class ExampleController {

    private final ExampleRepository repository;

    public ExampleController(ExampleRepository repository) {
        this.repository = repository;
    }

    // Fetch all records
    @GetMapping("/examples")
    public List<Example> getAllExamples() {
        return repository.findAll();
    }

    // Fetch by name
    @GetMapping("/examples/name")
    public List<Example> getByName(@RequestParam("name") String name) {
        return repository.findByName(name);
    }
    
 // Insert single record
    @PostMapping("/examples")
    public Example createExample(@RequestBody Example example) {
        return repository.save(example);
    }
}

