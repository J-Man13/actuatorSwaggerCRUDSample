package org.sample.actuatorSwaggerCRUDSample.controller;

import io.swagger.annotations.ApiOperation;
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
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
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
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addUser(@RequestBody CrmUserAdditionRequestDto crmUserAdditionRequestDto) {
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
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateUser(@PathVariable("id") String id,@RequestBody CrmUserUpdateRequestDto crmUserUpdateRequestDto) {
        LOGGER.trace(new CommonLoggingObject(String.format("Extracting user by %s id from crm users service for update",id),crmUserUpdateRequestDto));
        CrmUserDao crmUserDao = crmUserService.findById(id);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service for update",id),crmUserDao));
        crmUserDao = crmUserMapper.updateCrmUserDaoByCrmUserUpdateRequestDto(crmUserDao,crmUserUpdateRequestDto);
        crmUserDao = crmUserService.update(crmUserDao);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service after update",id),crmUserDao));
        CrmUserUpdateResponceDto crmUserUpdateResponceDto = new CrmUserUpdateResponceDto(crmUserDao);
        CommonResponseDTO<CrmUserUpdateResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user was successfully saved"));
        commonResponseDTO.setData(crmUserUpdateResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }
}