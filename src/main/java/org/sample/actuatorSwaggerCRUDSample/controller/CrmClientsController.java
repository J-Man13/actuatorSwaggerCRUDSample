package org.sample.actuatorSwaggerCRUDSample.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmClientMapper;
import org.sample.actuatorSwaggerCRUDSample.model.*;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmClientService;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/clients")
public class CrmClientsController {

    private final CommonLogger LOGGER;
    private final ICrmClientService crmClientsService;
    private final CrmClientMapper crmClientMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String FIND_CRM_USER_BY_ID_SUCCESS = "FIND_CRM_USER_BY_ID_SUCCESS";
    private final String FIND_CRM_USER_BY_NAME_SUCCESS = "FIND_CRM_USER_BY_NAME_SUCCESS";
    private final String CRM_USER_SAVED_SUCCESSFULLY = "CRM_USER_SAVED_SUCCESSFULLY";
    private final String CRM_USER_UPDATED_SUCCESSFULLY = "CRM_USER_UPDATED_SUCCESSFULLY";

    public CrmClientsController(@Autowired @Qualifier("crmClientService") ICrmClientService crmClientsService,
                                @Autowired CrmClientMapper crmClientMapper,
                                @Autowired @Qualifier("multiLanguageFileComponent")IMultiLanguageComponent multiLanguageComponent,
                                @Autowired @Qualifier("trace-logger") CommonLogger LOGGER){
        this.crmClientsService = crmClientsService;
        this.crmClientMapper = crmClientMapper;
        this.LOGGER = LOGGER;
        this.multiLanguageComponent = multiLanguageComponent;
    }

    @ApiOperation(
            value = "Extraction of crm client by id from mongo db",
            notes = "Nothing super fishy, just extraction of crm client by auto generated mongo db id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user data was successfully extracted"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "Crm user mongo document with id was not found"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmClientExtractionResponceDto>> findClientById(@PathVariable("id") String id){
        LOGGER.trace("Extracting user by id from crm users service","id",id);
        CrmClient crmClient = crmClientsService.findById(id);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service",id),"crmUser", crmClient);
        CrmClientExtractionResponceDto crmClientExtractionResponceDto = new CrmClientExtractionResponceDto(crmClient);
        CommonResponseDTO<CrmClientExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),"success",FIND_CRM_USER_BY_ID_SUCCESS,String.format(multiLanguageComponent.getMessageByKey(FIND_CRM_USER_BY_ID_SUCCESS),id));
        commonResponseDTO.setData(crmClientExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Extraction of crm clients list by name from mongo db",
            notes = "Nothing super fishy, just extraction of crm clients list by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm users list data was successfully extracted"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "There was not any crm user mongo document with such name"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @GetMapping(value = "/attributes/name/{name}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmClientByNameExtractionResponceDto>> findClientsByName(@PathVariable("name") String name){
        LOGGER.trace("Extracting list of users by name from crm users service","name",name);
        List<CrmClient> crmClientList = crmClientsService.findByName(name);
        LOGGER.info(String.format("Extracted crm users by %s name from crm users service",name),"crmUserList", crmClientList);
        CrmClientByNameExtractionResponceDto crmClientByNameExtractionResponceDto = new CrmClientByNameExtractionResponceDto(crmClientList);
        CommonResponseDTO<CrmClientByNameExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),"success",FIND_CRM_USER_BY_NAME_SUCCESS,String.format(multiLanguageComponent.getMessageByKey(FIND_CRM_USER_BY_NAME_SUCCESS),name));
        commonResponseDTO.setData(crmClientByNameExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Addition of crm client to mongo db",
            notes = "Nothing super fishy, just addition of crm client to mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully saved"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmClientAdditionResponceDto>> addClient(@RequestBody @Valid CrmClientAdditionRequestDto crmClientAdditionRequestDto) {
        LOGGER.trace("Adding user via crm users save service","crmUserAdditionRequestDto", crmClientAdditionRequestDto);
        CrmClient crmClient = crmClientMapper.crmUserAdditionRequestDtoToCrmUser(crmClientAdditionRequestDto);
        crmClient = crmClientsService.save(crmClient);
        LOGGER.trace("User saved at crm users service","crmUser", crmClient);
        CrmClientAdditionResponceDto crmClientAdditionResponceDto = new CrmClientAdditionResponceDto(crmClient);
        CommonResponseDTO<CrmClientAdditionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success",CRM_USER_SAVED_SUCCESSFULLY,multiLanguageComponent.getMessageByKey(CRM_USER_SAVED_SUCCESSFULLY)));
        commonResponseDTO.setData(crmClientAdditionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Update of crm client at mongo db",
            notes = "Nothing super fishy, just update of crm client at mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully updated"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "Crm user mongo document with id was not found"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserUpdateResponceDto>> updateClient(@PathVariable("id") String id,@RequestBody CrmClientUpdateRequestDto crmClientUpdateRequestDto) {
        LOGGER.trace(String.format("Extracting user by %s id from crm users service for update",id),"crmUserUpdateRequestDto", crmClientUpdateRequestDto);
        CrmClient crmClient = crmClientsService.findById(id);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service for update",id),"crmUser", crmClient);
        crmClient = crmClientMapper.updateCrmUserByCrmUserUpdateRequestDto(crmClient, crmClientUpdateRequestDto);
        crmClient = crmClientsService.update(crmClient);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service after update",id),"crmUser", crmClient);
        CrmUserUpdateResponceDto crmUserUpdateResponceDto = new CrmUserUpdateResponceDto(crmClient);
        CommonResponseDTO<CrmUserUpdateResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success",CRM_USER_UPDATED_SUCCESSFULLY,multiLanguageComponent.getMessageByKey(CRM_USER_UPDATED_SUCCESSFULLY)));
        commonResponseDTO.setData(crmUserUpdateResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }
}