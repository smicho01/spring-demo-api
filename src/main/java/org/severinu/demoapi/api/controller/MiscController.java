package org.severinu.demoapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MiscController {

    @GetMapping("/healthcheck")
    public ResponseEntity<String> getAllMessages() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
