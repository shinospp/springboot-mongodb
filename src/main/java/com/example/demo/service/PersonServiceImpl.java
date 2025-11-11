package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.collection.Person;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService{

        @Autowired
        private PersonRepository personRepository;

        @Override
        public String save(Person person){
           return personRepository.save(person).getPersonId();
        }

        @Override
        public List<Person> getPersonStartWith(String name) {
            // TODO Auto-generated method stub
            return personRepository.findByFirstNameStartsWith(name);

        }

        @Override
        public List<Person> getAllPerson() {
            // TODO Auto-generated method stub
            Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
              // .and(Sort.by(Sort.Direction.DESC, "email"));
            return personRepository.findAll(sort);
        }

        @Override
        public void deletePerson(String id) {
            // TODO Auto-generated method stub
              personRepository.deleteById(id);
        }


        @Override
        public List<Person> getByPersonAge(Integer min, Integer max) {
            // TODO Auto-generated method stub
            return personRepository.findPersonByAgeBetween(min, max);
    }

        



}
