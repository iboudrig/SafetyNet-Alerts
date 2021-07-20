package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class CommunityEmailService {

    private IDataService dataService;

    public CommunityEmailService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get email addresses of all the inhabitants of the city.
     * @param city a String
     * @return Iterable<String> if the city exists, null otherwise
     */
    public Iterable<String> getEmailsByCity(String city) {
        List<Person> persons = dataService.getPersonsByCity(city);
        if(persons != null){
            List<String> emails = persons.stream().map(Person::getEmail).collect(Collectors.toList());
            log.info("Emails retrieved");
            return emails;
        }
        log.error("Failed to retrieve the emails");
        return null;
    }
}
