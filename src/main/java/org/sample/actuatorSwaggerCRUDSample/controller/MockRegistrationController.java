package org.sample.actuatorSwaggerCRUDSample.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mock")
public class MockRegistrationController{

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
            return new ResponseEntity(string, HttpStatus.OK);
        else
            return new ResponseEntity(string,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
