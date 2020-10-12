package org.sample.actuatorSwaggerCRUDSample.configuration.security.actuator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonUnsuccessfulResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDesriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException, ServletException {
        //Extracting headers key/value pairs via stream api
        Map<String, List<String>> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h))
                ));

        LOGGER.fatal("Actuator Login failure. If you see lots of these messages, it means that there are authentication misfits with application's actuator accessors ,or someone is trying to guess actuator authentication credentials",new HashMap<String, String>() {{
            put("actuatorAuthenticationExceptionMessage", authenticationException.getMessage());
            put("actuatorAuthenticationExceptionStackTraceAsString", Throwables.getStackTraceAsString(authenticationException));
            put("actuatorAccessorRemoteHost",request.getRemoteHost());
            put("actuatorAccessorHeaders",headersMap.toString());
        }});
        response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(new ObjectMapper().writeValueAsString(buildFailedActuatorAuthenticationDto(authenticationException)));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("AZC");
        super.afterPropertiesSet();
    }

    private CommonUnsuccessfulResponseDTO buildFailedActuatorAuthenticationDto(AuthenticationException authenticationException){
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(authenticationException.getStackTrace()[0].getClassName(),
                FAILED_ACTUATOR_AUTHENTICATION,
                String.format(multiLanguageComponent.getMessageByKey(FAILED_ACTUATOR_AUTHENTICATION),authenticationException.getMessage()),
                authenticationException.getClass().getCanonicalName());
        return new CommonUnsuccessfulResponseDTO(HttpServletResponse.SC_UNAUTHORIZED, "error", errorDesriptor.getMessageKey(),errorDesriptor.getMessage(), errorDesriptor);
    }
}
