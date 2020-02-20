package org.sample.actuatorSwaggerCRUDSample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping
    public ResponseEntity sampleGet(){
        return null;
    }

    @PostMapping
    public ResponseEntity samplePost(){
        return null;
    }
}
