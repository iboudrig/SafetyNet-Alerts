package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class PhoneAlertService {

    private IDataService dataService;

    public PhoneAlertService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get phone numbers of all persons covered by a fire station
     * @param firestation A known station number
     * @return Iterable<String> or null
     */
    public Iterable<String> getPhones(int firestation) {

        log.debug("searching for addresses corresponding to firestation " + firestation);
        List<FireStation> fireStations = dataService.getAllFirestationsByStationNumber(firestation);
        if (!fireStations.isEmpty()){
            List<String> addresses = fireStations.stream().map(FireStation::getAddress).collect(Collectors.toList());
            List<Person> personsByAddress = dataService.getAllPersonsByAddress(addresses);
            if (personsByAddress != null){
                log.debug("Retrieving phone numbers of people covered by firestation " + firestation);
                List<String> phones = personsByAddress.stream().map(Person::getPhone).collect(Collectors.toList());
                log.info("Phone number(s) successfully retrieved!");
                return phones;
            }
            log.error("No one found");
            return null;
        }
        log.error("No address corresponding to firestation " + firestation);
        return null;
    }
}
