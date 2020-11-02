package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import com.google.common.base.Throwables;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.CrmUserEntityNotFoundException;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalHandledException;

import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserEntity;
import org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm.CrmUserRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CrmUsersJwtUserDetailsService implements UserDetailsService {

    private final CrmUserRepository crmUserRepository;
    private final CommonLogger LOGGER;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND="CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND";
    private final String CRM_USER_BY_LOGIN_EXTRACTION_SUCCESSFULLY_FOUND="CRM_USER_BY_LOGIN_EXTRACTION_SUCCESSFULLY_FOUND";
    private final String CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION = "CRM_USER_BY_LOGIN_EXTRACTION_REPOSITORY_EXCEPTION";

    private final CommonResponseDTO commonResponseDTO;

    public CrmUsersJwtUserDetailsService(final CrmUserRepository crmUserRepository,
                                         final @Qualifier("trace-logger") CommonLogger LOGGER,
                                         final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent,
                                         final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO){
        this.crmUserRepository=crmUserRepository;
        this.LOGGER=LOGGER;
        this.multiLanguageComponent=multiLanguageComponent;
        this.commonResponseDTO=commonResponseDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            CrmUserEntity crmUserEntity = crmUserRepository.findByLogin(username).orElse(null);
            Objects.requireNonNull(crmUserEntity,()->{
                LOGGER.debug("CRM user mysql entity with given login was not found","login",username);
                throw new CrmUserEntityNotFoundException(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND,
                        String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_NOT_FOUND),username));
            });
            List<GrantedAuthority> grantedAuthorities = crmUserEntity.getRoles().stream()
                    .map(role->new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

            commonResponseDTO.getMessages()
                    .add(new CommonMessageDTO(
                            "success",
                            CRM_USER_BY_LOGIN_EXTRACTION_SUCCESSFULLY_FOUND,
                            String.format(multiLanguageComponent.getMessageByKey(CRM_USER_BY_LOGIN_EXTRACTION_SUCCESSFULLY_FOUND),username)));
            return new User(crmUserEntity.getLogin(),crmUserEntity.getCryptedPassword(), grantedAuthorities);

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
}
