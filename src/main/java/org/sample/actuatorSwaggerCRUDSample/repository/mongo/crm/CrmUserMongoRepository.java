package org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CrmUserMongoRepository {
    private MongoTemplate crmMongoTemplate;

    public CrmUserMongoRepository(@Autowired @Qualifier("crmMongoTemplate") MongoTemplate crmMongoTemplate){
        this.crmMongoTemplate = crmMongoTemplate;
    }

    public CrmUserMongoDocument save(CrmUserMongoDocument user){
        return crmMongoTemplate.save(user);
    }

    public CrmUserMongoDocument findById(String id){
        return crmMongoTemplate.findById(id,CrmUserMongoDocument.class);
    }
}
