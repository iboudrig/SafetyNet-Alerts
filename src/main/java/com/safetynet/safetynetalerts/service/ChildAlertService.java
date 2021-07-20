package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.utils.AlertUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class ChildAlertService {

    private IDataService dataService;

    public ChildAlertService(IDataService dataService){
        this.dataService = dataService;
    }

    /**
     * Get a list of children(age <= 18) living at a certain address as well as their family members
     * @param address A known address
     * @return Information about the children or an empty object
     */
    public ChildAlertDTO getChildrenByAddress(String address) {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO(List.of(), List.of());
        log.debug("searching for persons living at " + address);
        List<Person> personsByAddress = dataService.getAllPersonsByAddress(address);
        if (personsByAddress != null){
            log.debug("searching for their medical records");
            List<MedicalRecord> medicalRecords = personsByAddress.stream()
                    .map(person -> dataService.getMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName()))
                    .collect(Collectors.toList());

            log.debug("Extracting children");
            List<ChildDTO> children = medicalRecords.stream().filter(mr -> AlertUtils.calculateAge(mr.getBirthdate()) <= 18)
                    .map(mr -> new ChildDTO(mr.getFirstName(), mr.getLastName(), AlertUtils.calculateAge(mr.getBirthdate())))
                    .collect(Collectors.toList());
            if (!children.isEmpty()){
                log.debug("Extracting family members");
                for (ChildDTO child : children){
                    personsByAddress.removeIf(person -> person.getFirstName().equals(child.getFirstName()) && person.getLastName().equals(child.getLastName()));
                }

                childAlertDTO.setChildren(children);
                childAlertDTO.setFamilyMembers(personsByAddress);
                log.info("ChildAlert data successfully retrieved");
            } else {
                log.error("No child found at " + address);
            }
        } else {
            log.error("No person found at " + address);
        }

        return childAlertDTO;
    }
}
