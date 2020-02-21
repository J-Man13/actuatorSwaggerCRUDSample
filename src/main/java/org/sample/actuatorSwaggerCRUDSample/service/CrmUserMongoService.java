package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalUnhandledException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserDao;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CrmUserMongoService implements ICrmUserService{

    private CrmUserMongoRepository crmUserMongoRepository;
    private CrmUserMapper crmUserMapper;

    public CrmUserMongoService(@Autowired @Qualifier("crmUserMongoRepository") CrmUserMongoRepository crmUserMongoRepository,
                               @Autowired CrmUserMapper crmUserMapper){
        this.crmUserMongoRepository = crmUserMongoRepository;
        this.crmUserMapper = crmUserMapper;
    }

    public CrmUserDao save(CrmUserDao crmUserDao){
        return null;
    }

    public CrmUserDao findById(String id){
        CrmUserMongoDocument crmUserMongoDocument = null;
        try{
            crmUserMongoDocument = crmUserMongoRepository.findById(id);
        }
        catch (Exception exception){
            throw new GlobalUnhandledException(String.format("Mongo Repository has thrown unhandled exception : %s", exception.getMessage()));
        }
        Objects.requireNonNull(crmUserMongoDocument,()->{
            throw new MongoDocumentNotFoundException(String.format("Mongo Document with %s id was not found ",id));
        });
        return crmUserMapper.UserMongoDocumentToCrmUserDao(crmUserMongoDocument);
    }
}
