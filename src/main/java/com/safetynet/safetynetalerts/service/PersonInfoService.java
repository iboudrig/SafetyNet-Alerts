package com.safetynet.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.AlertUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class PersonInfoService {

    private IDataService dataService;

    public PersonInfoService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get some information about people based on the first and last names
     * @param firstName A String
     * @param lastName A String
     * @return Iterable<PersonInfoDTO> or null
     */
    public Iterable<PersonInfoDTO> getPersonInfo(String firstName, String lastName) {
        List<PersonInfoDTO> personInfos = new ArrayList<>();
        List<Person> persons = dataService.getPersonsByFirstNameAndLastName(firstName, lastName);
        List<MedicalRecord> medicalRecords = dataService.getAllMedicalRecordsByFirstNameAndLastName(firstName, lastName);
        if ((persons != null) && (medicalRecords != null)){
            for (int i = 0; i < persons.size(); i++){
                Person person = persons.get(i);
                MedicalRecord medicalRecord = medicalRecords.get(i);
                personInfos.add(new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
                        AlertUtils.calculateAge(medicalRecord.getBirthdate()), person.getEmail(), medicalRecord.getMedications(),
                        medicalRecord.getAllergies()));
            }
        }
        if (!personInfos.isEmpty()){
            log.info("person info successfully retrieved.");
            return personInfos;
        }
        log.error("Failed to retrieve person info");
        return null;
    }
}
