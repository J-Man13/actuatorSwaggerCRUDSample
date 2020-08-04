package org.sample.actuatorSwaggerCRUDSample.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.model.CommonUnsuccessfulResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final String FAILED_AUTHENTICATION = "FAILED_AUTHENTICATION";
    private final IMultiLanguageComponent multiLanguageComponent;

    public AuthenticationEntryPoint(@Autowired @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent){
        this.multiLanguageComponent = multiLanguageComponent;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorDesriptor errorDesriptor = new ErrorDesriptor(authEx.getStackTrace()[0].getClassName(),
                FAILED_AUTHENTICATION,
                multiLanguageComponent.getMessageByKey(FAILED_AUTHENTICATION),
                authEx.getClass().getCanonicalName());

        CommonUnsuccessfulResponseDTO commonUnsuccessfulResponseDTO = new CommonUnsuccessfulResponseDTO(HttpServletResponse.SC_UNAUTHORIZED, "error", errorDesriptor.getMessageKey(),errorDesriptor.getMessage(), errorDesriptor);

        PrintWriter writer = response.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(commonUnsuccessfulResponseDTO));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("AZC");
        super.afterPropertiesSet();
    }
}
