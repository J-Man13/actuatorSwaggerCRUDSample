package org.sample.actuatorSwaggerCRUDSample.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sample.actuatorSwaggerCRUDSample.mapper.CrmUserMapper;
import org.sample.actuatorSwaggerCRUDSample.model.*;
import org.sample.actuatorSwaggerCRUDSample.service.ICrmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDTO<CrmUserExtractionResponceDto>> findUserById(@PathVariable("id") String id){
        LOGGER.trace(new CommonLoggingObject("Extracting user by id from crm users service",id));
        CrmUserDao crmUserDao = crmUserService.findById(id);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm user by %s id from crm users service",id),crmUserDao));
        CrmUserExtractionDto crmUserExtractionDto = crmUserMapper.crmUserDaoToCrmUserExtractionDto(crmUserDao);
        CrmUserExtractionResponceDto crmUserExtractionResponceDto = new CrmUserExtractionResponceDto(crmUserExtractionDto);
        CommonResponseDTO<CrmUserExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user data was successfully extracted"));
        commonResponseDTO.setData(crmUserExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @GetMapping("/attributes/name/{name}")
    public ResponseEntity<CommonResponseDTO<CrmUserExtractionResponceDto>> findUsersByName(@PathVariable("name") String name){
        LOGGER.trace(new CommonLoggingObject("Extracting users by name from crm users service",name));
        CrmUserDao crmUserDao = crmUserService.findById(name);
        LOGGER.info(new CommonLoggingObject(String.format("Extracted crm users by %s name from crm users service",name),crmUserDao));
        CrmUserExtractionDto crmUserExtractionDto = crmUserMapper.crmUserDaoToCrmUserExtractionDto(crmUserDao);
        CrmUserExtractionResponceDto crmUserExtractionResponceDto = new CrmUserExtractionResponceDto(crmUserExtractionDto);
        CommonResponseDTO<CrmUserExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user data was successfully extracted"));
        commonResponseDTO.setData(crmUserExtractionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody CrmUserAdditionRequestDto crmUserAdditionRequestDto) {
        LOGGER.trace(new CommonLoggingObject("Adding user via crm users service",crmUserAdditionRequestDto));
        CrmUserDao crmUserDao = crmUserMapper.crmUserAdditionRequestDtoToCrmUserDao(crmUserAdditionRequestDto);
        crmUserDao = crmUserService.save(crmUserDao);
        CrmUserAdditionResponceDto crmUserAdditionResponceDto = new CrmUserAdditionResponceDto(crmUserDao);
        CommonResponseDTO<CrmUserAdditionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user was successfully saved"));
        commonResponseDTO.setData(crmUserAdditionResponceDto);
        return ResponseEntity.ok(commonResponseDTO);
    }
}