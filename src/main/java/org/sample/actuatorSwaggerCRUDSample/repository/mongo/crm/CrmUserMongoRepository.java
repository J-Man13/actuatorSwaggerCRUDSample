package org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CrmUserMongoRepository {
    private MongoTemplate crmMongoTemplate;

    public CrmUserMongoRepository(@Autowired @Qualifier("crmMongoTemplate") MongoTemplate crmMongoTemplate){
        this.crmMongoTemplate = crmMongoTemplate;
    }

    public CrmUserMongoDocument save(CrmUserMongoDocument crmUserMongoDocument){
        crmUserMongoDocument.setDtstamp(LocalDateTime.now());
        return crmMongoTemplate.save(crmUserMongoDocument);
    }

    public CrmUserMongoDocument findById(String id){
        return crmMongoTemplate.findById(id,CrmUserMongoDocument.class);
    }

    public List<CrmUserMongoDocument> findByName(String name){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return crmMongoTemplate.find(query,CrmUserMongoDocument.class);
    }
}
