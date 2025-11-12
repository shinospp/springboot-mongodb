package com.example.demo.service;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.collection.Person;

public interface PersonService {

    public String save(Person person);

    public List<Person> getPersonStartWith(String name);

    public List<Person> getAllPerson();

    public void deletePerson(String id);

    public List<Person> getByPersonAge(Integer minAge, Integer maxAge);

    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable);

    public List<Document> geOldestPersonByCity();

}
