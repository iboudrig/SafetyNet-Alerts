package com.safetynet.safetynetalerts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.model.Person;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class PersonService {

    private IDataService dataService;

    private List<Person> personList;

    public PersonService(IDataService dataService){
        this.dataService = dataService;
        personList = dataService.getAllPersons();
    }

    /**
     * Add a person
     * @param person Person object
     * @return The added object or null
     */
    public Person addPerson(Person person){

        if(!person.getFirstName().isBlank() && !person.getLastName().isBlank()){
            personList.add(person);
            log.info(person.getFirstName() + " " + person.getLastName() + " successfully added!");
            return person;
        }
        log.error("Failed to add the person");

        return null;
    }

    /**
     * Get all persons
     * @return List<Person>
     */
    public List<Person> list(){
        return personList;
    }

    /**
     * Update an existing person
     * @param person Person object
     * @return The updated object or null
     */
    public Person updatePerson(Person person) {

        Optional<Person> updatedPerson = personList.stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()) )
                .peek(p -> {
                    p.setAddress(person.getAddress());
                    p.setCity(person.getCity());
                    p.setZip(person.getZip());
                    p.setPhone(person.getPhone());
                    p.setEmail(person.getEmail());
                })
                .findFirst();
        if (updatedPerson.isPresent()){
            log.info(person.getFirstName() + " " +person.getLastName() + "'s record successfully updated!");
            return updatedPerson.get();
        }else {
            log.error("Failed to update person "+person.getFirstName() + " " +person.getLastName());
        }

        return null;
    }

    /**
     * Delete an existing person
     * @param firstName person's first name
     * @param lastName person's last name
     * @return True or false depending on the successfulness or failure of the operation
     */
    public boolean deletePerson(String firstName, String lastName) {

       boolean removed = personList.removeIf(p -> firstName.equals(p.getFirstName()) && lastName.equals(p.getLastName()));
       if (removed){
           log.info(firstName + " " +lastName + "'s record successfully deleted!");
       }else {
           log.error("Failed to delete person "+firstName + " " +lastName);
       }
       return removed;
    }
}
