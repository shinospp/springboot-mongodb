package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoMappingContext context) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory(MongoClients.create(), "test"),
                context
        );
        converter.setTypeMapper(new DefaultMongoTypeMapper(null)); // disable _class
        return converter;
    }

    @Bean
    public MongoTemplate mongoTemplate(MappingMongoConverter converter) {
        return new MongoTemplate(new org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory(MongoClients.create(), "test"), converter);
    }
}
