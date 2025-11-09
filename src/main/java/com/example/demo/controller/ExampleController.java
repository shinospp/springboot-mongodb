package com.example.demo.controller;

import com.example.demo.dto.ExampleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.collection.Example;
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
    //Introduced DTO Object for request data binding
    @PostMapping("/examples")
    public Example createExample(@RequestBody ExampleDTO exampleDto) {
        Example example= new Example();
        example.setName(exampleDto.name());
        return repository.save(example);
    }


    //Delete by ID
    @DeleteMapping("/examples/{id}")
    public ResponseEntity<String> deleteExample(@PathVariable("id") String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Document not found with id: " + id);
        }
    }

    //Delete all documents with the given name
    @DeleteMapping("/examples/by-name")
    public ResponseEntity<String> deleteByName(@RequestParam("name") String name) {
        List<Example> examples = repository.findByName(name);

        if (examples.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No documents found with name: " + name);
        }

        repository.deleteAll(examples);
        return ResponseEntity.ok(examples.size() + " document(s) deleted with name: " + name);
    }


}

