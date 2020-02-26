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

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class CrmUsersController {

    private ICrmUserService crmUserService;
    private CrmUserMapper crmUserMapper;



    public CrmUsersController(@Autowired @Qualifier("crmUserMongoService") ICrmUserService crmUserService,
                              @Autowired CrmUserMapper crmUserMapper){
        this.crmUserService = crmUserService;
        this.crmUserMapper = crmUserMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseDTO<CrmUserExtractionResponceDto>> findUserById(@PathVariable("id") String id){
        CrmUserDao crmUserDao = crmUserService.findById(id);
        CrmUserExtractionDto crmUserExtractionDto = crmUserMapper.crmUserDaoToCrmUserExtractionDto(crmUserDao);
        CrmUserExtractionResponceDto crmUserExtractionResponceDto = new CrmUserExtractionResponceDto(crmUserExtractionDto);
        CommonResponseDTO<CrmUserExtractionResponceDto> commonResponseDTO = new CommonResponseDTO(HttpStatus.OK.value(),new CommonMessageDTO("success","Crm user data was successfully extracted"));
        commonResponseDTO.setData(crmUserExtractionResponceDto);
        return new ResponseEntity(commonResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/attributes/name/{name}")
    public ResponseEntity findUsersByNameIgnoringCase(@PathVariable("name") String name)
    {
        return null;
    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody CrmUserAdditionRequestDto crmUserAdditionRequestDto){
        return new ResponseEntity(crmUserAdditionRequestDto,HttpStatus.OK);
    }

}
