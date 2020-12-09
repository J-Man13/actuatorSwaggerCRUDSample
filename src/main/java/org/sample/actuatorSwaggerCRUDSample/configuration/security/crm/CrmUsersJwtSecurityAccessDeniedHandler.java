package org.sample.actuatorSwaggerCRUDSample.configuration.security.crm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.message.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.mapper.CommonMapper;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDesriptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CrmUsersJwtSecurityAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper;
    private final CommonResponseDTO commonResponseDTO;
    private final CommonMapper commonMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    public CrmUsersJwtSecurityAccessDeniedHandler(final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO,
                                                  final CommonMapper commonMapper,
                                                  final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent) {
        this.mapper=new ObjectMapper();
        this.commonResponseDTO=commonResponseDTO;
        this.commonMapper=commonMapper;
        this.multiLanguageComponent=multiLanguageComponent;
    }

    private final String CRM_ORIGINATOR_OF_AUTHENTICATED_REQUEST_FORBIDDEN = "CRM_ORIGINATOR_OF_AUTHENTICATED_REQUEST_FORBIDDEN";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        ErrorDesriptor errorDesriptor = new ErrorDesriptor(accessDeniedException.getStackTrace()[0].getClassName(),
                CRM_ORIGINATOR_OF_AUTHENTICATED_REQUEST_FORBIDDEN,
                String.format(multiLanguageComponent.getMessageByKey(CRM_ORIGINATOR_OF_AUTHENTICATED_REQUEST_FORBIDDEN),request.getRequestURI()),
                accessDeniedException.getClass().getCanonicalName());

        commonResponseDTO.setStatusCodeMessageDtoErrorDescriptorAndInitDate(
                HttpServletResponse.SC_FORBIDDEN,
                new CommonMessageDTO("error",
                        errorDesriptor.getMessageKey(),
                        errorDesriptor.getMessage()),
                errorDesriptor
        );

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(mapper.writeValueAsString(commonMapper.cloneCommonResponseDTO(commonResponseDTO)));
    }
}
