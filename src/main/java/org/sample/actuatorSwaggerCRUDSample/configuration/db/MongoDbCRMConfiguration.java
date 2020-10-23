package org.sample.actuatorSwaggerCRUDSample.configuration.db;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm",
        mongoTemplateRef = "crmMongoTemplate")
public class MongoDbCRMConfiguration {

    @Bean("crmMongoDbFactory")
    public MongoDbFactory crmMongoDbFactory(final @Value("${spring.data.mongodb.crm.uri}") String mongoDbCRMUri) {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.connectTimeout(10000);
        optionsBuilder.socketTimeout(10000);
        optionsBuilder.serverSelectionTimeout(10000);
        return new SimpleMongoDbFactory(new MongoClientURI(mongoDbCRMUri,optionsBuilder));
    }


    @Bean("crmMongoTemplate")
    public MongoTemplate crmMongoTemplate(final @Qualifier("crmMongoDbFactory") MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean("crmMongoTransactionManager")
    MongoTransactionManager transactionManager(final @Qualifier("crmMongoDbFactory")MongoDbFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }
}