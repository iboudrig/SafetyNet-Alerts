package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.service.FireService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/fire")
@Slf4j
public class FireController {

    FireService fireService;

    public FireController(FireService fireService){
        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<FireDTO> getAllPersonAndStationByAddress(@RequestParam String address){
        log.info("getAllPersonAndStationByAddress request "+ address);
        FireDTO result = fireService.getAllPersonAndStationByAddress(address);
        if (!result.getResidents().isEmpty()){
            log.info("getAllPersonAndStationByAddress response "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        log.error("getAllPersonAndStationByAddress response "+ result);
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}

