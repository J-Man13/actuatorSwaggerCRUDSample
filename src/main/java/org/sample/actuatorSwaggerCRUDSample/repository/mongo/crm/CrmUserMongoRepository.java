package org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CrmUserMongoRepository extends MongoRepository<CrmUserMongoDocument,String> {
    List<CrmUserMongoDocument> findAllByName(String name);
}
