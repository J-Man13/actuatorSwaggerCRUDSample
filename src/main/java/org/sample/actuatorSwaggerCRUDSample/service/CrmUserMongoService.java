package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalHandledException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmUserMongoDocument;
import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Objects;

@Service
public class CrmUserMongoService implements ICrmUserService{

    private final CommonLogger LOGGER;
    private CrmUserMongoRepository crmUserMongoRepository;
    private CrmUserMapper crmUserMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String CRM_USER_EXCEPTION_FAILED_UPDATE = "CRM_USER_EXCEPTION_FAILED_UPDATE";
    private final String CRM_USER_EXCEPTION_FAILED_SAVE = "CRM_USER_EXCEPTION_FAILED_SAVE";
    private final String CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION = "CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION";
    private final String CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND = "CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND";
    private final String CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION = "CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION";
    private final String CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND = "CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND";

    public CrmUserMongoService(@Autowired @Qualifier("crmUserMongoRepository") CrmUserMongoRepository crmUserMongoRepository,
                               @Autowired CrmUserMapper crmUserMapper,
                               @Autowired @Qualifier("trace-logger") CommonLogger LOGGER,
                               @Autowired @Qualifier("multiLanguageFileComponent")IMultiLanguageComponent multiLanguageComponent){
        this.crmUserMongoRepository = crmUserMongoRepository;
        this.crmUserMapper = crmUserMapper;
        this.LOGGER = LOGGER;
        this.multiLanguageComponent = multiLanguageComponent;
    }

    @Override
    public CrmUser update(CrmUser crmUser) {
        CrmUserMongoDocument crmUserMongoDocument = crmUserMapper.crmUserToCrmUserMongoDocument(crmUser);
        try {
            crmUserMongoDocument = crmUserMongoRepository.save(crmUserMongoDocument);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM Mongo repository has thrown exception during document update : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_EXCEPTION_FAILED_UPDATE,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_EXCEPTION_FAILED_UPDATE), dataAccessException.getMessage()));
        }
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Override
    public CrmUser save(CrmUser crmUser){
        CrmUserMongoDocument crmUserMongoDocument = crmUserMapper.crmUserToCrmUserMongoDocument(crmUser);
        try {
            crmUserMongoDocument = crmUserMongoRepository.save(crmUserMongoDocument);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM Mongo repository has thrown exception during document save : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_EXCEPTION_FAILED_SAVE,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_EXCEPTION_FAILED_SAVE), dataAccessException.getMessage()));
        }
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Override
    public CrmUser findById(String id){
        CrmUserMongoDocument crmUserMongoDocument;
        try{
            crmUserMongoDocument = crmUserMongoRepository.findById(id).orElse(null);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM users mongo repository has thrown exception during document by id extraction : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION), dataAccessException.getMessage()));
        }

        Objects.requireNonNull(crmUserMongoDocument,()->{
            LOGGER.debug(String.format("CRM user mongo document with %s id was not found",id));
            throw new MongoDocumentNotFoundException(CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND),id));
        });
        return crmUserMapper.crmUserMongoDocumentToCrmUser(crmUserMongoDocument);
    }

    @Override
    public List<CrmUser> findByName(String name) {
        List<CrmUserMongoDocument> crmUserMongoDocumentList;
        try {
            crmUserMongoDocumentList = crmUserMongoRepository.findAllByName(name);
        }catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM users mongo repository has thrown exception during documents by name extraction : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION), dataAccessException.getMessage()));
        }

        if (CollectionUtils.isEmpty(crmUserMongoDocumentList)){
            LOGGER.debug(String.format("There was not any CRM user mongo documents with %s name",name));
            throw new MongoDocumentNotFoundException(CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND),name));
        }
        return crmUserMapper.crmUserMongoDocumentListToCrmUserList(crmUserMongoDocumentList);
    }
}
