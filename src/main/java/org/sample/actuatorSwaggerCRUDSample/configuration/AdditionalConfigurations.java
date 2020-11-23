package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.custom.exception.GlobalCommonException;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AdditionalConfigurations {

    private final IMultiLanguageComponent multiLanguageComponent;

    public AdditionalConfigurations(final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent){
        this.multiLanguageComponent=multiLanguageComponent;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @RequestScope
    public CommonResponseDTO commonResponseDTO(final @Value("${info.build.archiveBaseName}") String infoBuildArchiveBaseName){
        return new CommonResponseDTO(infoBuildArchiveBaseName);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setMultiLanguageComponentInGlobalCommonException(){
        GlobalCommonException.setMultiLanguageComponent(multiLanguageComponent);
    }
}
