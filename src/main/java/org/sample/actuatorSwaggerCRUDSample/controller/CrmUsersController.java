package org.sample.actuatorSwaggerCRUDSample.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.*;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmUserService;
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
    private final Logger LOGGER = LogManager.getLogger("trace_logs");

    private ICrmUserService crmUserService;
    private CrmUserMapper crmUserMapper;

    public CrmUsersController(@Autowired @Qualifier("crmUserMongoService") ICrmUserService crmUserService,
                              @Autowired CrmUserMapper crmUserMapper){
        this.crmUserService = crmUserService;
        this.crmUserMapper = crmUserMapper;
    }

    @ApiOperation(
            value = "Extraction of crm user by id from mongo db",
            notes = "Nothing super fishy, just extraction of crm user by auto generated mongo db id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user data was successfully extracted"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 404, message = "Crm user mongo document with id was not found"),
            @ApiResponse(code = 500, message = "Unhandled exception at mongo repository or somewhere else , advising to contact for the logs")
    })
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUserExtractionResponceDto>> findUserById(@PathVariable("id") String id){
        LOGGER.trace(new CommonLoggingObject("Extracting user by id from crm users service",id));
        CrmUserDao crmUserDao = crmUserService.findById(id);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service",id),crmUserDao));
        CrmUserExtractionResponceDto crmUserExtractionResponceDto = new CrmUserExtractionResponceDto(crmUserDao);
        CommonResponseDTO<CrmUserExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user data was successfully extracted"));
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
            @ApiResponse(code = 500, message = "Unhandled exception at mongo repository or somewhere else , advising to contact for the logs")
    })
    @GetMapping(value = "/attributes/name/{name}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommonResponseDTO<CrmUsersByNameExtractionResponceDto>> findUsersByName(@PathVariable("name") String name){
        LOGGER.trace(new CommonLoggingObject("Extracting list of users by name from crm users service",name));
        List<CrmUserDao> crmUserDaoList = crmUserService.findByName(name);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm users by %s name from crm users service",name),crmUserDaoList));
        CrmUsersByNameExtractionResponceDto crmUsersByNameExtractionResponceDto = new CrmUsersByNameExtractionResponceDto(crmUserDaoList);
        CommonResponseDTO<CrmUsersByNameExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user data was successfully extracted"));
        commonResponseDTO.setData(crmUsersByNameExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Addition of crm user to mongo db",
            notes = "Nothing super fishy, just addition of crm user to mongo db")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crm user was successfully saved"),
            @ApiResponse(code = 400, message = "Request is in unreadable format or could not pass request validation"),
            @ApiResponse(code = 500, message = "Unhandled exception at mongo repository or somewhere else , advising to contact for the logs")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addUser(@RequestBody @Valid CrmUserAdditionRequestDto crmUserAdditionRequestDto) {
        LOGGER.trace(new CommonLoggingObject("Adding user via crm users service",crmUserAdditionRequestDto));
        CrmUserDao crmUserDao = crmUserMapper.crmUserAdditionRequestDtoToCrmUserDao(crmUserAdditionRequestDto);
        crmUserDao = crmUserService.save(crmUserDao);
        LOGGER.trace(new CommonLoggingObject("User saved at crm users service",crmUserDao));
        CrmUserAdditionResponceDto crmUserAdditionResponceDto = new CrmUserAdditionResponceDto(crmUserDao);
        CommonResponseDTO<CrmUserAdditionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user was successfully saved"));
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
            @ApiResponse(code = 500, message = "Unhandled exception at mongo repository or somewhere else , advising to contact for the logs")
    })
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateUser(@PathVariable("id") String id,@RequestBody CrmUserUpdateRequestDto crmUserUpdateRequestDto) {
        LOGGER.trace(new CommonLoggingObject(String.format("Extracting user by %s id from crm users service for update",id),crmUserUpdateRequestDto));
        CrmUserDao crmUserDao = crmUserService.findById(id);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service for update",id),crmUserDao));
        crmUserDao = crmUserMapper.updateCrmUserDaoByCrmUserUpdateRequestDto(crmUserDao,crmUserUpdateRequestDto);
        crmUserDao = crmUserService.update(crmUserDao);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service after update",id),crmUserDao));
        CrmUserUpdateResponceDto crmUserUpdateResponceDto = new CrmUserUpdateResponceDto(crmUserDao);
        CommonResponseDTO<CrmUserUpdateResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user was successfully updated"));
        commonResponseDTO.setData(crmUserUpdateResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @ApiOperation(
            value = "Mock service",
            notes = "Nothing super fishy, just mock service which randomly determines if some mock operation was registered")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Mock operation registered"),
            @ApiResponse(code = 500, message = "Unhandled exception during mock operation registration")
    })
    @PostMapping(value = "/registration",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity mockOperationRegistationService(@RequestBody String string){
        boolean isSuccessful = Math.random() < 0.5;
        if (isSuccessful)
            return new ResponseEntity(string,HttpStatus.OK);
        else
            return new ResponseEntity(string,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}