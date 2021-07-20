package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.ResidentDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.AlertUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class FireService {

    private IDataService dataService;

    public FireService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get the list of persons living at a given address plus the station number serving it
     * @param address a String
     * @return FireDTO object
     */
    public FireDTO getAllPersonAndStationByAddress(String address) {
        FireDTO fireDTO = new FireDTO(List.of(), 0);
        List<Person> persons = dataService.getAllPersonsByAddress(address);
        if (persons != null){
            List<ResidentDTO> residents = persons.stream()
                    .map(person -> {
                        MedicalRecord medicalRecord = dataService.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                        return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
                                AlertUtils.calculateAge(medicalRecord.getBirthdate()), medicalRecord.getMedications(), medicalRecord.getAllergies());
                    })
                    .collect(Collectors.toList());
            if(!residents.isEmpty()){
                log.info("Information on resident(s) collected");
                fireDTO.setResidents(residents);
            } else {
                log.error("Failed to collect information on resident(s)");
            }

            FireStation fireStation = dataService.getFirestationByAddress(address);
            if(fireStation != null){
                log.info("Station number retrieved");
                fireDTO.setStation(fireStation.getStation());
            }
        } else {
            log.error("No one found at " + address);
        }

        return fireDTO;
    }
}
