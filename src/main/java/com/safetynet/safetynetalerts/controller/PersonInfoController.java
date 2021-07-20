package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.service.PersonInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/personInfo")
@Slf4j
public class PersonInfoController {

    PersonInfoService personInfoService;

    public PersonInfoController(PersonInfoService personInfoService){
        this.personInfoService = personInfoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<PersonInfoDTO>> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName){
        log.info("Request getPersonInfo "+ firstName + " " +lastName);
        Iterable<PersonInfoDTO> result = personInfoService.getPersonInfo(firstName, lastName);
        if (result != null){
            log.info("getPersonInfo response "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        log.error("getPersonInfo response "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
