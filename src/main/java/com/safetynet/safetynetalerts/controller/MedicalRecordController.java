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

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/medicalRecord")
@Slf4j
public class MedicalRecordController {

    private MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService){
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<Iterable<MedicalRecord>> list(){
        log.info("Request list medical records");
        Iterable<MedicalRecord> medicalRecords = medicalRecordService.list();
        log.info("List medical records response => "+medicalRecords);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        log.info("addMedicalRecord request "+medicalRecord);
        MedicalRecord addedRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        if(addedRecord != null){
            log.info("addMedicalRecord response "+addedRecord);
            return new ResponseEntity<>(addedRecord, HttpStatus.CREATED);
        }
        log.error("addMedicalRecord response "+null);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        log.info("updateMedicalRecord request "+medicalRecord);
        MedicalRecord updatedRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
        if (updatedRecord != null){
            log.info("updateMedicalRecord response => "+updatedRecord);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        }
        log.error("updateMedicalRecord response => "+null);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName){
        log.info("Request deleteMedicalRecord "+ firstName + " " +lastName);
        boolean removed = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if(removed){
            log.info("Response deleteMedicalRecord => "+true);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        log.error("Response deleteMedicalRecord => "+false);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
