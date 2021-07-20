package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.CommunityEmailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/communityEmail")
@Slf4j
public class CommunityEmailController {
    CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService){
        this.communityEmailService = communityEmailService;
    }

    @GetMapping
    public ResponseEntity<Iterable<String>> getEmailsByCity(@RequestParam String city){
        log.info("getEmailsByCity request "+ city);
        Iterable<String> emails = communityEmailService.getEmailsByCity(city);
        if (emails != null){
            log.info("getEmailsByCity response "+ emails);
            return new ResponseEntity<>(emails, HttpStatus.OK);
        }
        log.error("getEmailsByCity response "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
