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

@Configuration
public class MongoDbCRMConfiguration {

    @Value("${spring.data.mongodb.crm.uri}")
    private String mongoDbCRMUri;

    @Bean("crmMongoDbFactory")
    @Primary
    public MongoDbFactory crmMongoDbFactory() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.connectTimeout(5000);
        optionsBuilder.socketTimeout(5000);
        optionsBuilder.serverSelectionTimeout(5000);
        return new SimpleMongoDbFactory(new MongoClientURI(mongoDbCRMUri,optionsBuilder));
    }

    @Bean("crmMongoTemplate")
    @Primary
    public MongoTemplate crmMongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(crmMongoDbFactory());
        return mongoTemplate;
    }
}
