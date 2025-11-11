package com.example.demo.service;

import java.util.List;

import com.example.demo.collection.Person;

public interface PersonService {

    public String save(Person person);

    public List<Person> getPersonStartWith(String name);

    public List<Person> getAllPerson();

    public void deletePerson(String id);

    public List<Person> getByPersonAge(Integer minAge, Integer maxAge);

}
