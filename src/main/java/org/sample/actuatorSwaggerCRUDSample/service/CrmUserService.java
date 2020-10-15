package org.sample.actuatorSwaggerCRUDSample.service;

import com.google.common.base.Throwables;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.CrmUserEntityNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalHandledException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserMySqlEntity;
import org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm.CrmUserMySqlRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class CrmUserService implements ICrmUserService {

    private final CrmUserMySqlRepository crmUserMySqlRepository;
    private final CommonLogger LOGGER;
    private final CrmUserMapper crmUserMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    public CrmUserService(CrmUserMySqlRepository crmUserMySqlRepository,
                          @Qualifier("trace-logger") CommonLogger LOGGER,
                          CrmUserMapper crmUserMapper,
                          @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent){
        this.crmUserMySqlRepository=crmUserMySqlRepository;
        this.LOGGER=LOGGER;
        this.crmUserMapper=crmUserMapper;
        this.multiLanguageComponent = multiLanguageComponent;
    }

    private final String CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION = "CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION";
    private final String CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND = "CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND";

    @Override
    public CrmUser findUserByLogin(String login) {
        try {
            CrmUserMySqlEntity crmUserMySqlEntity = crmUserMySqlRepository.findByLogin(login).orElse(null);
            Objects.requireNonNull(crmUserMySqlEntity,()->{
                LOGGER.debug("CRM user mysql entity with given login was not found","login",login);
                throw new CrmUserEntityNotFoundException(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND),login));
            });
            return crmUserMapper.crmUserMySqlEntityToCrmUser(crmUserMySqlEntity);
        }
        catch (CrmUserEntityNotFoundException crmUserEntityNotFoundException){
            throw crmUserEntityNotFoundException;
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error("CRM mysql repository has thrown exception during entity by login extraction",new HashMap<String, String>() {{
                put("dataAccessExceptionMessage", dataAccessException.getMessage());
                put("dataAccessExceptionStackTraceAsString", Throwables.getStackTraceAsString(dataAccessException));
            }});
            throw new GlobalHandledException(CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION,String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION), dataAccessException.getMessage()));
        }
    }

    @Override
    public CrmUser save(CrmUser crmUser) {
        return null;
    }

}
