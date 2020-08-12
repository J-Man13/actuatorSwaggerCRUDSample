package org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmClientMongoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CrmClientMongoRepository extends MongoRepository<CrmClientMongoDocument,String> {
    List<CrmClientMongoDocument> findAllByName(String name);
}
