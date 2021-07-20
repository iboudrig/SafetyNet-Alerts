package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.ResidentDTO;
import com.safetynet.safetynetalerts.dto.StationsDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.AlertUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class StationsService {

    private IDataService dataService;

    public StationsService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get all persons covered by a provided list of stations
     * @param stations A list of station numbers
     * @return Iterable<StationsDTO> or null
     */
    public Iterable<StationsDTO> getFamiliesCovered(List<Integer> stations) {
        List<StationsDTO> families = new ArrayList<>();
        List<FireStation> fireStations = dataService.getAllFirestationsByStationNumber(stations);
        if (!fireStations.isEmpty()){
            List<String> addresses = fireStations.stream().map(FireStation::getAddress).collect(Collectors.toList());
            for (String address : addresses){
                List<Person> persons = dataService.getAllPersonsByAddress(address);
                if(persons != null){
                    List<ResidentDTO> residents = persons.stream()
                            .map(person -> {
                                MedicalRecord medicalRecord = dataService.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                                return new ResidentDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
                                        AlertUtils.calculateAge(medicalRecord.getBirthdate()), medicalRecord.getMedications(), medicalRecord.getAllergies());
                            })
                            .collect(Collectors.toList());

                    families.add(new StationsDTO(address,residents));
                }
            }
        }
        if (!families.isEmpty()){
            log.info("Information on families covered by the stations retrieved.");
            return families;
        }
        log.error("Failed to retrieve information on families covered by the stations");
        return null;
    }
}
