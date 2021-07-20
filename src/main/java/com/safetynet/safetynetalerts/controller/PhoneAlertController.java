package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.service.PhoneAlertService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/phoneAlert")
@Slf4j
public class PhoneAlertController {

    PhoneAlertService phoneAlertService;

    public PhoneAlertController(PhoneAlertService phoneAlertService){
        this.phoneAlertService = phoneAlertService;
    }

    @GetMapping
    public ResponseEntity<Iterable<String>> getPhones(@RequestParam int firestation){
        log.info("getPhones request "+ firestation);
        Iterable<String> phones = phoneAlertService.getPhones(firestation);
        if (phones != null){
            log.info("getPhones response "+ phones);
            return new ResponseEntity<>(phones, HttpStatus.OK);
        }
        log.error("getPhones response "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
