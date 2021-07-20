package com.safetynet.safetynetalerts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/childAlert")
@Slf4j
public class ChildAlertController {
	private ChildAlertService childAlertService;

    public ChildAlertController(ChildAlertService childAlertService){
        this.childAlertService = childAlertService;
    }

    @GetMapping
    public ResponseEntity<ChildAlertDTO> getChildrenByAddress(@RequestParam String address){
        log.info("getChildrenByAddress request "+ address);
        ChildAlertDTO result = childAlertService.getChildrenByAddress(address);
        if (!result.getChildren().isEmpty()){
            log.info("getChildrenByAddress response "+ result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        log.error("getChildrenByAddress response "+ result);
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

}
