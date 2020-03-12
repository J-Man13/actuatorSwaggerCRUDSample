package org.sample.actuatorSwaggerCRUDSample.configuration;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm",
        mongoTemplateRef = "crmMongoTemplate")
public class MongoDbCRMConfiguration {
    private String mongoDbCRMUri;

    public MongoDbCRMConfiguration(@Value("${spring.data.mongodb.crm.uri}") String mongoDbCRMUri){
        this.mongoDbCRMUri = mongoDbCRMUri;
    }

    @Primary
    @Bean("crmMongoDbFactory")
    public MongoDbFactory crmMongoDbFactory() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.connectTimeout(10000);
        optionsBuilder.socketTimeout(10000);
        optionsBuilder.serverSelectionTimeout(10000);
        return new SimpleMongoDbFactory(new MongoClientURI(mongoDbCRMUri,optionsBuilder));
    }

    @Bean("crmMongoTemplate")
    @Primary
    public MongoTemplate crmMongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(crmMongoDbFactory());
        return mongoTemplate;
    }
}