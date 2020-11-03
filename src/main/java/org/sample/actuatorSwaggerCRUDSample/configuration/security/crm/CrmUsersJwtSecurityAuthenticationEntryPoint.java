package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.actuatorSwaggerCRUDSample.mapper.CommonMapper;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CrmUsersJwtSecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;
    private final CommonResponseDTO commonResponseDTO;
    private final CommonMapper commonMapper;

    public CrmUsersJwtSecurityAuthenticationEntryPoint(final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO,
                                                       final CommonMapper commonMapper) {
        this.mapper = new ObjectMapper();
        this.commonResponseDTO=commonResponseDTO;
        this.commonMapper=commonMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(mapper.writeValueAsString(commonMapper.cloneCommonResponseDTO(commonResponseDTO)));
    }
}
