package org.sample.actuatorSwaggerCRUDSample.service;

import com.google.common.base.Throwables;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.CrmUserEntityNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalHandledException;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserEntity;
import org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm.CrmUserRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service
public class CrmUserService implements ICrmUserService {

    private final CrmUserRepository crmUserRepository;
    private final CommonLogger LOGGER;
    private final CrmUserMapper crmUserMapper;
    private final IMultiLanguageComponent multiLanguageComponent;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CrmUserService(final CrmUserRepository crmUserRepository,
                          final @Qualifier("trace-logger") CommonLogger LOGGER,
                          final CrmUserMapper crmUserMapper,
                          final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent,
                          final BCryptPasswordEncoder bCryptPasswordEncoder){
        this.crmUserRepository = crmUserRepository;
        this.LOGGER=LOGGER;
        this.crmUserMapper=crmUserMapper;
        this.multiLanguageComponent = multiLanguageComponent;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    private final String CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION = "CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION";
    private final String CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND = "CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND";
    private final String CRM_USER_REPOSITORY_EXCEPTION_FAILED_SAVE = "CRM_USER_REPOSITORY_EXCEPTION_FAILED_SAVE";

    @Override
    public CrmUser findUserByLogin(String login) {
        try {
            CrmUserEntity crmUserEntity = crmUserRepository.findByLogin(login).orElse(null);
            Objects.requireNonNull(crmUserEntity,()->{
                LOGGER.debug("CRM user mysql entity with given login was not found","login",login);
                throw new CrmUserEntityNotFoundException(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND,
                        String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND),login));
            });
            return crmUserMapper.crmUserEntityToCrmUser(crmUserEntity);
        }
        catch (CrmUserEntityNotFoundException crmUserEntityNotFoundException){
            throw crmUserEntityNotFoundException;
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error("CRM mysql repository has thrown exception during entity by login extraction",new HashMap<String, String>() {{
                put("dataAccessExceptionMessage", dataAccessException.getMessage());
                put("dataAccessExceptionStackTraceAsString", Throwables.getStackTraceAsString(dataAccessException));
            }});
            throw new GlobalHandledException(CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION,
                    String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION), dataAccessException.getMessage()));
        }
    }

    @Transactional("mySqlCRMTransactionManager")
    @Override
    public CrmUser save(CrmUser crmUser,String unencryptedPassword) {
        CrmUserEntity crmUserEntity = crmUserMapper.crmUserToCrmUserEntity(crmUser);
        crmUserEntity.setCryptedPassword(bCryptPasswordEncoder.encode(unencryptedPassword));
        crmUserEntity.setRegistrationDate(LocalDateTime.now());
        try {
            crmUserEntity = crmUserRepository.save(crmUserEntity);
            return crmUserMapper.crmUserEntityToCrmUser(crmUserEntity);
        }
        catch (DataAccessException dataAccessException){
            LOGGER.error("CRM Users repository has thrown exception during entity save",new HashMap<String, String>() {{
                put("dataAccessExceptionMessage", dataAccessException.getMessage());
                put("dataAccessExceptionStackTraceAsString", Throwables.getStackTraceAsString(dataAccessException));
            }});
            throw new GlobalHandledException(CRM_USER_REPOSITORY_EXCEPTION_FAILED_SAVE,
                    String.format(multiLanguageComponent.getMessageByKey(CRM_USER_REPOSITORY_EXCEPTION_FAILED_SAVE), dataAccessException.getMessage()));
        }
    }

}
