package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalHandledException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.MongoDocumentNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmClientMapper;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.model.CrmClient;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmClientMongoDocument;
import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmClientMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;


import java.util.List;
import java.util.Objects;

@Service
public class CrmClientService implements ICrmClientService {

    private final CommonLogger LOGGER;
    private CrmClientMongoRepository crmClientMongoRepository;
    private CrmClientMapper crmClientMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String CRM_USER_EXCEPTION_FAILED_UPDATE = "CRM_USER_EXCEPTION_FAILED_UPDATE";
    private final String CRM_USER_EXCEPTION_FAILED_SAVE = "CRM_USER_EXCEPTION_FAILED_SAVE";
    private final String CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION = "CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION";
    private final String CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND = "CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND";
    private final String CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION = "CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION";

    public CrmClientService(@Autowired @Qualifier("crmClientMongoRepository") CrmClientMongoRepository crmClientMongoRepository,
                            @Autowired CrmClientMapper crmClientMapper,
                            @Autowired @Qualifier("trace-logger") CommonLogger LOGGER,
                            @Autowired @Qualifier("multiLanguageFileComponent")IMultiLanguageComponent multiLanguageComponent){
        this.crmClientMongoRepository = crmClientMongoRepository;
        this.crmClientMapper = crmClientMapper;
        this.LOGGER = LOGGER;
        this.multiLanguageComponent = multiLanguageComponent;
    }

    @Override
    public CrmClient update(CrmClient crmClient) {
        CrmClientMongoDocument crmClientMongoDocument = crmClientMapper.crmUserToCrmUserMongoDocument(crmClient);
        try {
            crmClientMongoDocument = crmClientMongoRepository.save(crmClientMongoDocument);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM Mongo repository has thrown exception during document update : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_EXCEPTION_FAILED_UPDATE,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_EXCEPTION_FAILED_UPDATE), dataAccessException.getMessage()));
        }
        return crmClientMapper.crmUserMongoDocumentToCrmUser(crmClientMongoDocument);
    }

    @Override
    public CrmClient save(CrmClient crmClient){
        CrmClientMongoDocument crmClientMongoDocument = crmClientMapper.crmUserToCrmUserMongoDocument(crmClient);
        try {
            crmClientMongoDocument = crmClientMongoRepository.save(crmClientMongoDocument);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM Mongo repository has thrown exception during document save : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_EXCEPTION_FAILED_SAVE,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_EXCEPTION_FAILED_SAVE), dataAccessException.getMessage()));
        }
        return crmClientMapper.crmUserMongoDocumentToCrmUser(crmClientMongoDocument);
    }

    @Override
    public CrmClient findById(String id){
        CrmClientMongoDocument crmClientMongoDocument;
        try{
            crmClientMongoDocument = crmClientMongoRepository.findById(id).orElse(null);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM users mongo repository has thrown exception during document by id extraction : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_ID_EXCEPTION), dataAccessException.getMessage()));
        }

        Objects.requireNonNull(crmClientMongoDocument,()->{
            LOGGER.debug(String.format("CRM user mongo document with %s id was not found",id));
            throw new MongoDocumentNotFoundException(CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_ID_NOT_FOUND),id));
        });
        return crmClientMapper.crmUserMongoDocumentToCrmUser(crmClientMongoDocument);
    }

    @Override
    public List<CrmClient> findByName(String name) {
        List<CrmClientMongoDocument> crmClientMongoDocumentList;
        try {
            crmClientMongoDocumentList = crmClientMongoRepository.findAllByName(name);
        }catch (DataAccessException dataAccessException){
            LOGGER.error(String.format("CRM users mongo repository has thrown exception during documents by name extraction : %s", dataAccessException.getMessage()),"dataAccessException",dataAccessException);
            throw new GlobalHandledException(CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_NAME_EXCEPTION), dataAccessException.getMessage()));
        }

        if (CollectionUtils.isEmpty(crmClientMongoDocumentList)){
            LOGGER.debug(String.format("There was not any CRM user mongo documents with %s name",name));
            String CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND = "CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND";
            throw new MongoDocumentNotFoundException(CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_MONGO_DOCUMENT_BY_NAME_NOT_FOUND),name));
        }
        return crmClientMapper.crmUserMongoDocumentListToCrmUserList(crmClientMongoDocumentList);
    }
}
