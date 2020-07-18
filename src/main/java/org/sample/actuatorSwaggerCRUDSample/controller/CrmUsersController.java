package org.sample.actuatorSwaggerCRUDSample.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.*;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmUserService;
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
@RequestMapping("/users")
public class CrmUsersController {

    private final CommonLogger LOGGER;
    private final ICrmUserService crmUserService;
    private final CrmUserMapper crmUserMapper;
    private final IMultiLanguageComponent multiLanguageComponent;

    private final String FIND_CRM_USER_BY_ID_SUCCESS = "FIND_CRM_USER_BY_ID_SUCCESS";
    private final String FIND_CRM_USER_BY_NAME_SUCCESS = "FIND_CRM_USER_BY_NAME_SUCCESS";
    private final String CRM_USER_SAVED_SUCCESSFULLY = "CRM_USER_SAVED_SUCCESSFULLY";
    private final String CRM_USER_UPDATED_SUCCESSFULLY = "CRM_USER_UPDATED_SUCCESSFULLY";

    public CrmUsersController(@Autowired @Qualifier("crmUserMongoService") ICrmUserService crmUserService,
                              @Autowired CrmUserMapper crmUserMapper,
                              @Autowired @Qualifier("multiLanguageFileComponent")IMultiLanguageComponent multiLanguageComponent,
                              @Autowired @Qualifier("trace-logger") CommonLogger LOGGER){
        this.crmUserService = crmUserService;
        this.crmUserMapper = crmUserMapper;
        this.LOGGER = LOGGER;
        this.multiLanguageComponent = multiLanguageComponent;
    }

    @ApiOperation(
            value = "Extraction of crm user by id from mongo db",
            notes = "Nothing super fishy, just extraction of crm user by auto generated mongo db id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user data was successfully extracted"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "Crm user mongo document with id was not found"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserExtractionResponceDto>> findUserById(@PathVariable("id") String id){
        LOGGER.trace("Extracting user by id from crm users service","id",id);
        CrmUser crmUser = crmUserService.findById(id);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service",id),"crmUser", crmUser);
        CrmUserExtractionResponceDto crmUserExtractionResponceDto = new CrmUserExtractionResponceDto(crmUser);
        CommonResponseDTO<CrmUserExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),"success",FIND_CRM_USER_BY_ID_SUCCESS,String.format(multiLanguageComponent.getMessageByKey(FIND_CRM_USER_BY_ID_SUCCESS),id));
        commonResponseDTO.setData(crmUserExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Extraction of crm users list by name from mongo db",
            notes = "Nothing super fishy, just extraction of crm users list by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm users list data was successfully extracted"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "There was not any crm user mongo document with such name"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @GetMapping(value = "/attributes/name/{name}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUsersByNameExtractionResponceDto>> findUsersByName(@PathVariable("name") String name){
        LOGGER.trace("Extracting list of users by name from crm users service","name",name);
        List<CrmUser> crmUserList = crmUserService.findByName(name);
        LOGGER.info(String.format("Extracted crm users by %s name from crm users service",name),"crmUserList", crmUserList);
        CrmUsersByNameExtractionResponceDto crmUsersByNameExtractionResponceDto = new CrmUsersByNameExtractionResponceDto(crmUserList);
        CommonResponseDTO<CrmUsersByNameExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),"success",FIND_CRM_USER_BY_NAME_SUCCESS,String.format(multiLanguageComponent.getMessageByKey(FIND_CRM_USER_BY_NAME_SUCCESS),name));
        commonResponseDTO.setData(crmUsersByNameExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Addition of crm user to mongo db",
            notes = "Nothing super fishy, just addition of crm user to mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully saved"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserAdditionResponceDto>> addUser(@RequestBody @Valid CrmUserAdditionRequestDto crmUserAdditionRequestDto) {
        LOGGER.trace("Adding user via crm users save service","crmUserAdditionRequestDto",crmUserAdditionRequestDto);
        CrmUser crmUser = crmUserMapper.crmUserAdditionRequestDtoToCrmUser(crmUserAdditionRequestDto);
        crmUser = crmUserService.save(crmUser);
        LOGGER.trace("User saved at crm users service","crmUser", crmUser);
        CrmUserAdditionResponceDto crmUserAdditionResponceDto = new CrmUserAdditionResponceDto(crmUser);
        CommonResponseDTO<CrmUserAdditionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success",CRM_USER_SAVED_SUCCESSFULLY,multiLanguageComponent.getMessageByKey(CRM_USER_SAVED_SUCCESSFULLY)));
        commonResponseDTO.setData(crmUserAdditionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Update of crm user at mongo db",
            notes = "Nothing super fishy, just update of crm user at mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully updated"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "Crm user mongo document with id was not found"),
            @ApiResponse(code = 500, message = "Technical mismatch, advising to contact for the logs, to handle the issue")
    })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserUpdateResponceDto>> updateUser(@PathVariable("id") String id,@RequestBody CrmUserUpdateRequestDto crmUserUpdateRequestDto) {
        LOGGER.trace(String.format("Extracting user by %s id from crm users service for update",id),"crmUserUpdateRequestDto",crmUserUpdateRequestDto);
        CrmUser crmUser = crmUserService.findById(id);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service for update",id),"crmUser", crmUser);
        crmUser = crmUserMapper.updateCrmUserByCrmUserUpdateRequestDto(crmUser,crmUserUpdateRequestDto);
        crmUser = crmUserService.update(crmUser);
        LOGGER.info(String.format("Extracted crm user by %s id from crm users service after update",id),"crmUser", crmUser);
        CrmUserUpdateResponceDto crmUserUpdateResponceDto = new CrmUserUpdateResponceDto(crmUser);
        CommonResponseDTO<CrmUserUpdateResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success",CRM_USER_UPDATED_SUCCESSFULLY,multiLanguageComponent.getMessageByKey(CRM_USER_UPDATED_SUCCESSFULLY)));
        commonResponseDTO.setData(crmUserUpdateResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }
}