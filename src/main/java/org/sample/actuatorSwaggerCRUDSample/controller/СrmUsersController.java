package org.sample.actuatorSwaggerCRUDSample.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class СrmUsersController {


    public СrmUsersController(){
    }

    @GetMapping(value = "/test",produces = MediaType.TEXT_HTML_VALUE)
    public String test(){
        return "test";
    }

    @GetMapping(value = "/authenticated/test",produces = MediaType.TEXT_HTML_VALUE)
    public String authenticatedTest(){
        return "authenticated test";
    }

    @GetMapping(value = "/registration",produces = MediaType.TEXT_HTML_VALUE)
    public String registration(){
        return "registration";
    }


}
