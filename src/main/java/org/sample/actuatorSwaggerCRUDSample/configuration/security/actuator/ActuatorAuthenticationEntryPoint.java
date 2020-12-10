package org.sample.actuatorSwaggerCRUDSample.configuration.security.actuator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.message.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.mapper.CommonMapper;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDescriptor;

import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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
    private final ObjectMapper mapper;
    private final CommonResponseDTO commonResponseDTO;
    private final CommonMapper commonMapper;

    public ActuatorAuthenticationEntryPoint(final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent,
                                            final @Qualifier("trace-logger") CommonLogger LOGGER,
                                            final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO,
                                            final CommonMapper commonMapper)
    {
        this.multiLanguageComponent = multiLanguageComponent;
        this.LOGGER = LOGGER;
        this.mapper=new ObjectMapper();
        this.commonResponseDTO=commonResponseDTO;
        this.commonMapper=commonMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException
    {
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

        ErrorDescriptor errorDescriptor = new ErrorDescriptor(authenticationException.getStackTrace()[0].getClassName(),
                FAILED_ACTUATOR_AUTHENTICATION,
                String.format(multiLanguageComponent.getMessageByKey(FAILED_ACTUATOR_AUTHENTICATION,"en"),authenticationException.getMessage()),
                authenticationException.getClass().getCanonicalName());

        commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                HttpServletResponse.SC_UNAUTHORIZED,
                new CommonMessageDTO("error",
                        errorDescriptor.getMessageKey(),
                        errorDescriptor.getMessage()),
                errorDescriptor
                );
        response.getWriter().println(mapper.writeValueAsString(commonMapper.cloneCommonResponseDTO(commonResponseDTO)));
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        setRealmName("internal.azericard");
        super.afterPropertiesSet();
    }
}
