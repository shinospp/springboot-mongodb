package com.example.demo.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PersonService;

import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.collection.Person;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public String save(@RequestBody Person person) {
        // TODO: process POST request

        return personService.save(person);
    }

    @GetMapping("/get")
    public List<Person> getPersonStartWith(@RequestParam("name") String name) {
        return personService.getPersonStartWith(name);
    }

    @GetMapping("/getAll")
    public List<Person> getAll() {
        return personService.getAllPerson();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        personService.deletePerson(id);

    }

    @GetMapping("/age")
    public List<Person> getByPersonAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return personService.getByPersonAge(minAge, maxAge);
    }

    @GetMapping("/search")
    public Page<Person> searchPerson(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "5") Integer pageSize) {

        // Ensure valid values
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 5; // fallback default
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Person> page = personService.search(name, minAge, maxAge, city, pageable);

        return page;
    }

    @GetMapping("/oldestPerson")
    public List<Document> geOldestPerson() {
        return personService.geOldestPersonByCity();
    }

}
