package org.sample.actuatorSwaggerCRUDSample.configuration;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoDbConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoDbUri;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientOptions.Builder options = new MongoClientOptions.Builder().connectTimeout(5000);
        return new SimpleMongoDbFactory(new MongoClientURI(mongoDbUri,options));
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;
    }
}
