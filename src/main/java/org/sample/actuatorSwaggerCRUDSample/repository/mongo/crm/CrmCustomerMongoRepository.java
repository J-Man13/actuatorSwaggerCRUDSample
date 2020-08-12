package org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmCustomerMongoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CrmCustomerMongoRepository extends MongoRepository<CrmCustomerMongoDocument,String> {
    List<CrmCustomerMongoDocument> findAllByName(String name);
}
