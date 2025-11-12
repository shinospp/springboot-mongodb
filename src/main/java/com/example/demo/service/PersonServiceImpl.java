package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.example.demo.collection.Person;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);

    }

    @Override
    public List<Person> getAllPerson() {
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        // .and(Sort.by(Sort.Direction.DESC, "email"));
        return personRepository.findAll(sort);
    }

    @Override
    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer min, Integer max) {
        return personRepository.findPersonByAgeBetween(min, max);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name, "i"));
        }

        if (minAge != null && maxAge != null) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        } else if (minAge != null) {
            criteria.add(Criteria.where("age").gte(minAge));
        } else if (maxAge != null) {
            criteria.add(Criteria.where("age").lte(maxAge));
        }

        if (city != null && !city.isEmpty()) {
            criteria.add(Criteria.where("addresses.city").is(city));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        /*
         * Page<Person> people =PageableExecutionUtils.getPage(
         * mongoTemplate.find(query, Person.class),
         * pageable,
         * () -> mongoTemplate.count(query.skip(0).limit(0), Person.class));
         * 
         * return people;
         */

        // Execute and wrap results
        List<Person> results = mongoTemplate.find(query, Person.class);
        long total = mongoTemplate.count(Query.of(query).limit(0).skip(0), Person.class);
        logger.info("Total: " + total);
        return new PageImpl<>(results, pageable, total);
    }

    public List<Document> geOldestPersonByCity() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");

        GroupOperation groupOperation = Aggregation
                .group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        List<Document> person = mongoTemplate.aggregate(aggregation, Person.class, Document.class)
                .getMappedResults();

        return person;

    }

}
