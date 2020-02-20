package org.sample.actuatorSwaggerCRUDSample.controller;


import org.sample.actuatorSwaggerCRUDSample.repository.mongo.crm.CrmUserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class CrmUsersController {

    @Autowired
    @Qualifier("crmUserMongoRepository")
    private CrmUserMongoRepository crmUserMongoRepository;

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable("id") String id){
        return new ResponseEntity(crmUserMongoRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/attributes/name/{name}")
    public ResponseEntity findUsersByNameIgnoringCase(@PathVariable("name") String name)
    {
        return null;
    }

    @PostMapping
    public ResponseEntity addUser(){
        return null;
    }


}
