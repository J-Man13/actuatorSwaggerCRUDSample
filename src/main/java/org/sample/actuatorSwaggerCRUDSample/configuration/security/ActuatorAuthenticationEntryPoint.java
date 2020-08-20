package org.sample.actuatorSwaggerCRUDSample.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
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



@Component
public class ActuatorAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final CommonLogger LOGGER;
    private final String FAILED_ACTUATOR_AUTHENTICATION = "FAILED_ACTUATOR_AUTHENTICATION";
    private final IMultiLanguageComponent multiLanguageComponent;


    public ActuatorAuthenticationEntryPoint(@Autowired @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent,
                                            @Autowired @Qualifier("trace-logger") CommonLogger LOGGER ){
        this.multiLanguageComponent = multiLanguageComponent;
        this.LOGGER = LOGGER;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {


        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(new ObjectMapper().writeValueAsString(buildFailedActuatorAuthenticationDto(authEx)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("AZC");
        super.afterPropertiesSet();
    }

    private CommonUnsuccessfulResponseDTO buildFailedActuatorAuthenticationDto(AuthenticationException authEx){
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(authEx.getStackTrace()[0].getClassName(),
                FAILED_ACTUATOR_AUTHENTICATION,
                String.format(multiLanguageComponent.getMessageByKey(FAILED_ACTUATOR_AUTHENTICATION),authEx.getMessage()),
                authEx.getClass().getCanonicalName());
        return new CommonUnsuccessfulResponseDTO(HttpServletResponse.SC_UNAUTHORIZED, "error", errorDesriptor.getMessageKey(),errorDesriptor.getMessage(), errorDesriptor);
    }
}
