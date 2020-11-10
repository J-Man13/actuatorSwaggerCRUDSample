package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class AdditionalConfigurations {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @RequestScope
    public CommonResponseDTO commonResponseDTO(final @Value("${info.build.archiveBaseName}") String infoBuildArchiveBaseName){
        return new CommonResponseDTO(infoBuildArchiveBaseName);
    }
}
