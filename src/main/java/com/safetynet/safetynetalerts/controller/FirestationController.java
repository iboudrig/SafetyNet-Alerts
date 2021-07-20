package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FirestationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/firestation")
@Slf4j
public class FirestationController {

   private FirestationService firestationService;

    public FirestationController(FirestationService firestationService){
        this.firestationService = firestationService;
    }

    @PostMapping
    public ResponseEntity<FireStation> addFirestation(@RequestBody FireStation fireStation){
        log.info("addFirestation request "+ fireStation);
        FireStation mapping = firestationService.addFirestation(fireStation);
        if (mapping != null){
            log.info("addFirestation response "+ mapping);
            return new ResponseEntity<>(mapping, HttpStatus.CREATED);
        }
        log.error("addFirestation response "+ null);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<FireStation> updateFirestation(@RequestBody FireStation fireStation){
        log.info("updateFirestation request "+ fireStation);
        FireStation updatedFireStation = firestationService.updateFirestation(fireStation);
        if (updatedFireStation != null){
            log.info("updateFirestation response => "+ updatedFireStation);
            return new ResponseEntity<>(updatedFireStation, HttpStatus.OK);
        }
        log.error("updateFirestation response => "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFirestation(@RequestParam String address){
        log.info("deleteFirestation request "+ address);
        boolean removed = firestationService.deleteFirestation(address);
        if(removed){
            log.info("deleteFirestation response ==> "+ true);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        log.error("deleteFirestation response ==> "+ false);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<FireStationDTO> getPersonsCoveredByFirestation(@RequestParam int stationNumber){
        log.info("getPersonsCoveredByFirestation request "+ stationNumber);
        FireStationDTO result = firestationService.getPersonsCoveredByFirestation(stationNumber);
        if (result != null){
            log.info("getPersonsCoveredByFirestation response "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        log.error("getPersonsCoveredByFirestation response "+ null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
