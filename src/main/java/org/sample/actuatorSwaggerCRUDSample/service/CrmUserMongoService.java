package org.sample.actuatorSwaggerCRUDSample.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalUnhandledException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.CommonLoggingObject;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;
import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Objects;

@Service
public class CrmUserMongoService implements ICrmUserService{
    private final Logger LOGGER = LogManager.getLogger("trace_logs");
    private CrmUserMongoRepository crmUserMongoRepository;
    private CrmUserMapper crmUserMapper;

    public CrmUserMongoService(@Autowired @Qualifier("crmUserMongoRepository") CrmUserMongoRepository crmUserMongoRepository,
                               @Autowired CrmUserMapper crmUserMapper){
        this.crmUserMongoRepository = crmUserMongoRepository;
        this.crmUserMapper = crmUserMapper;
    }

    @Override
    public CrmUser update(CrmUser crmUser) {
        CrmUserMongoDocument crmUserMongoDocument = crmUserMapper.crmUserToCrmUserMongoDocument(crmUser);
        try {
            crmUserMongoDocument = crmUserMongoRepository.save(crmUserMongoDocument);
        }
        catch (Exception exception){
            LOGGER.error(new CommonLoggingObject(String.format("Mongo repository has thrown unhandled exception during document update : %s", exception.getMessage()),exception));
            throw new GlobalUnhandledException(String.format("Mongo repository has thrown unhandled exception during document update : %s", exception.getMessage()));
        }
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Override
    public CrmUser save(CrmUser crmUser){
        CrmUserMongoDocument crmUserMongoDocument = crmUserMapper.crmUserToCrmUserMongoDocument(crmUser);
        try {
            crmUserMongoDocument = crmUserMongoRepository.save(crmUserMongoDocument);
        }
        catch (Exception exception){
            LOGGER.error(new CommonLoggingObject(String.format("Mongo repository has thrown unhandled exception during document save : %s", exception.getMessage()),exception));
            throw new GlobalUnhandledException(String.format("Mongo repository has thrown unhandled exception during document save : %s", exception.getMessage()));
        }
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Transactional
    @Override
    public CrmUser saveAndRegisterExternally(CrmUser crmUser) {
        return null;
    }

    @Override
    public CrmUser findById(String id){
        CrmUserMongoDocument crmUserMongoDocument;
        try{
            crmUserMongoDocument = crmUserMongoRepository.findById(id);
        }
        catch (Exception exception){
            LOGGER.error(new CommonLoggingObject(String.format("Crm users mongo repository has thrown unhandled exception during document by id extraction : %s", exception.getMessage()),exception));
            throw new GlobalUnhandledException(String.format("Crm users mongo repository has thrown unhandled exception during document by id extraction : %s", exception.getMessage()));
        }

        Objects.requireNonNull(crmUserMongoDocument,()->{
            LOGGER.debug(new CommonLoggingObject(String.format("Crm user mongo document with %s id was not found",id),null));
            throw new MongoDocumentNotFoundException(String.format("Crm user mongo document with %s id was not found",id));
        });
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Override
    public List<CrmUser> findByName(String name) {
        List<CrmUserMongoDocument> crmUserMongoDocumentList = crmUserMongoRepository.findByName(name);
        if (CollectionUtils.isEmpty(crmUserMongoDocumentList)){
            LOGGER.debug(new CommonLoggingObject(String.format("There was not any crm user mongo document with %s name",name),null));
            throw new MongoDocumentNotFoundException(String.format("There was not any crm user mongo document with %s name",name));
        }
        return crmUserMapper.crmUserMongoDocumentListToCrmUserList(crmUserMongoDocumentList);
    }
}
