package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CrmUserMongoService implements ICrmUserService{

    @Autowired
    @Qualifier("crmUserMongoRepository")
    private CrmUserMongoRepository crmUserMongoRepository;

    @Autowired
    private CrmUserMapper crmUserMapper;

    public CrmUser save(CrmUser crmUser){
        return null;
    }

    public CrmUser findById(String id){
        CrmUserMongoDocument crmUserMongoDocument = null;
        try{
            crmUserMongoDocument = crmUserMongoRepository.findById(id);
        }
        catch (Exception exception){

        }
        Objects.requireNonNull(crmUserMongoDocument,()->{
            throw new MongoDocumentNotFoundException(new ErrorDesriptor(this.getClass().getCanonicalName(),String.format("Mongo Document with %s id was not found ",id)));
        });
        return crmUserMapper.UserMongoDocumentToCrmUser(crmUserMongoDocument);
    }
}
