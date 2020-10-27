package org.sample.actuatorSwaggerCRUDSample.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.mapper.CommonMapper;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.crm.dto.CrmUserAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.crm.dto.CrmUserAdditionResponseDto;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmUserService;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class СrmUsersController {

    private final CommonLogger LOGGER;
    private final CrmUserMapper crmUserMapper;
    private final ICrmUserService crmUserService;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String CRM_USER_SAVED_SUCCESSFULLY = "CRM_USER_SAVED_SUCCESSFULLY";

    private final CommonResponseDTO commonResponseDTO;
    private final CommonMapper commonMapper;

    public СrmUsersController(final @Qualifier("trace-logger") CommonLogger LOGGER,
                              final CrmUserMapper crmUserMapper,
                              final @Qualifier("crmUserService") ICrmUserService crmUserService,
                              final @Qualifier("multiLanguageFileComponent") IMultiLanguageComponent multiLanguageComponent,
                              final @Qualifier("commonResponseDTO") CommonResponseDTO commonResponseDTO,
                              final CommonMapper commonMapper){
        this.LOGGER = LOGGER;
        this.crmUserMapper = crmUserMapper;
        this.crmUserService = crmUserService;
        this.multiLanguageComponent = multiLanguageComponent;
        this.commonResponseDTO=commonResponseDTO;
        this.commonMapper=commonMapper;
    }



    @ApiOperation(
            value = "Addition of user to users mysql repository db",
            notes = "Nothing super fishy, just addition of crm user to mysql db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully saved"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserAdditionResponseDto>> add(@RequestBody @Valid CrmUserAdditionRequestDto crmUserAdditionRequestDto){
        LOGGER.trace("Adding crm user via crm user save service","crmUserAdditionRequestDto", crmUserAdditionRequestDto);
        CrmUser crmUser = crmUserMapper.crmUserAdditionRequestDtoToCrmUser(crmUserAdditionRequestDto);
        crmUser = crmUserService.save(crmUser,crmUserAdditionRequestDto.getPassword());
        LOGGER.trace("User saved at crm users service","crmUser", crmUser);
        CrmUserAdditionResponseDto crmUserAdditionResponseDto = new CrmUserAdditionResponseDto(crmUser);
        commonResponseDTO.setStatusCodeMessageDtoDataAndInitDate(
                HttpStatus.OK.value(),
                new CommonMessageDTO("success",
                        CRM_USER_SAVED_SUCCESSFULLY,
                        multiLanguageComponent.getMessageByKey(CRM_USER_SAVED_SUCCESSFULLY)),
                crmUserAdditionResponseDto);
        return ResponseEntity.ok(commonMapper.cloneCommonResponseDTO(commonResponseDTO));
    }
}
