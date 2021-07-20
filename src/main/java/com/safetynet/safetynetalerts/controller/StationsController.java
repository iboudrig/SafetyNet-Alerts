package com.safetynet.safetynetalerts.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.StationsDTO;
import com.safetynet.safetynetalerts.service.StationsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stations")
@Slf4j
public class StationsController {

    StationsService stationsService;

    public StationsController(StationsService stationsService){
        this.stationsService = stationsService;
    }

    @GetMapping
    public ResponseEntity<Iterable<StationsDTO>> getFamiliesCovered(@RequestParam List<Integer> stations){
        log.info("getFamiliesCovered request "+ stations);
        Iterable<StationsDTO> result = stationsService.getFamiliesCovered(stations);
        if (result != null){
            log.info("getFamiliesCovered response "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        log.error("getFamiliesCovered response "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
