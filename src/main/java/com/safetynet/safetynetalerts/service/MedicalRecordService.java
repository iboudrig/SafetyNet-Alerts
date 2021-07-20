package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.MedicalRecord;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class MedicalRecordService {

    private IDataService dataService;
    private List<MedicalRecord> medicalRecordList;

    public MedicalRecordService(IDataService dataService){
        this.dataService = dataService;
        medicalRecordList = dataService.getAllMedicalRecords();
    }

    /**
     * Get all medical records available
     * @return A list of medical records
     */
    public Iterable<MedicalRecord> list() {
        return medicalRecordList;
    }

    /**
     * Add a medical record
     * @param medicalRecord A MedicalRecord object with at least a valid first name and last name
     * @return The added record or null if the operation failed
     */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {

        if (!medicalRecord.getFirstName().isBlank() && !medicalRecord.getLastName().isBlank()){
            medicalRecordList.add(medicalRecord);
            log.info("Medical record successfully added");
            return medicalRecord;
        }
        log.error("Failed to add the medical record");
        return null;
    }

    /**
     * Update an existing medical record
     * @param medicalRecord A MedicalRecord object
     * @return The updated record or null
     */
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        Optional<MedicalRecord> updatedRecord = medicalRecordList.stream()
                .filter(mr -> mr.getFirstName().equals(medicalRecord.getFirstName()) && mr.getLastName().equals(medicalRecord.getLastName()))
                .peek(mr -> {
                    mr.setBirthdate(medicalRecord.getBirthdate());
                    mr.setMedications(medicalRecord.getMedications());
                    mr.setAllergies(medicalRecord.getAllergies());
                })
                .findFirst();
        if (updatedRecord.isPresent()){
            log.info(medicalRecord.getFirstName() + " " +medicalRecord.getLastName() + "'s medical record successfully updated!");
            return updatedRecord.get();
        }
        log.error("Failed to update "+medicalRecord.getFirstName() + " " +medicalRecord.getLastName() + "'s medical record");
        return null;
    }

    /**
     * Delete an existing medical record
     * @param firstName first name
     * @param lastName last name
     * @return True or false depending on the successfulness or failure of the operation
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        boolean removed = medicalRecordList.removeIf(mr -> firstName.equals(mr.getFirstName()) && lastName.equals(mr.getLastName()));
        if(removed){
            log.info("Medical record successfully removed");
        } else {
            log.error("Failed to remove the medical record");
        }
        return removed;
    }
}
