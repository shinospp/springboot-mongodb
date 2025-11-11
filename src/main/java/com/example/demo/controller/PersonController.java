package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
