package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonData;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JSONService implements IDataService {

    private final Resource jsonDataFile;
    private final JsonData jsonData;

    public JSONService(@Value("classpath:data.json") Resource jsonDataFile){
        this.jsonDataFile = jsonDataFile;
        jsonData = getDataFromJSONFile();
    }

    /**
     * Retrieve data from the JSON file
     * @return a JAVA object
     */
    public JsonData getDataFromJSONFile(){

        ObjectMapper mapper = new ObjectMapper();

        try {
            log.info("JSON data successfully retrieved");
            return mapper.readValue(jsonDataFile.getFile(), JsonData.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Person> getAllPersons(){
        return jsonData.getPersons();
    }

    @Override
    public List<FireStation> getAllFirestations(){
        return jsonData.getFirestations();
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords(){
        return jsonData.getMedicalrecords();
    }

    @Override
    public List<FireStation> getAllFirestationsByStationNumber(int stationNumber){
        List<FireStation> fireStations = jsonData.getFirestations().stream().filter(f -> f.getStation() == stationNumber)
                .collect(Collectors.toList());
        if (!fireStations.isEmpty()){
            log.info("Matching fire station(s) found");
            return fireStations;
        }
        log.error("No matching fire station found!");
        return List.of();
    }

    @Override
    public List<FireStation> getAllFirestationsByStationNumber(List<Integer> stations){
        List<FireStation> fireStations = jsonData.getFirestations().stream().filter(f -> stations.contains(f.getStation()))
                .collect(Collectors.toList());
        if (!fireStations.isEmpty()){
            log.info("Matching fire station(s) found");
            return fireStations;
        }
        log.error("No matching fire station found!");
        return List.of();
    }

    @Override
    public FireStation getFirestationByAddress(String address){
        Optional <FireStation> fireStation = jsonData.getFirestations().stream()
                .filter(f -> address.equals(f.getAddress()))
                .findFirst();
        if (fireStation.isPresent()){
            log.info("Matching fire station found");
            return fireStation.get();
        }
        log.error("No matching fire station");
        return null;
    }

    @Override
    public List<Person> getAllPersonsByAddress(List<String> addresses){
        List<Person> persons = jsonData.getPersons().stream().filter(p -> addresses.contains(p.getAddress()))
                .collect(Collectors.toList());
        if (!persons.isEmpty()){
            log.info("Matching person(s) found");
            return persons;
        }
        log.error("No match found!");
        return null;
    }

    @Override
    public List<Person> getAllPersonsByAddress(String address){
        List<Person> persons = jsonData.getPersons().stream().filter(p -> address.equals(p.getAddress()))
                .collect(Collectors.toList());
        if (!persons.isEmpty()){
            log.info("Matching person(s) found");
            return persons;
        }
        log.error("No match found!");
        return null;
    }

    @Override
    public List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName){
        List<Person> persons = jsonData.getPersons().stream()
                .filter(person -> firstName.equals(person.getFirstName()) && lastName.equals(person.getLastName()))
                .collect(Collectors.toList());
        if (!persons.isEmpty()){
            log.info("Matching person(s) found");
            return persons;
        }
        log.error("No match found!");
        return null;
    }

    @Override
    public List<Person> getPersonsByCity(String city){
        List<Person> persons = jsonData.getPersons().stream()
                .filter(person -> city.equals(person.getCity()))
                .collect(Collectors.toList());
        if (!persons.isEmpty()){
            log.info("Person(s) living in "+ city + " successfully retrieved");
            return persons;
        }
        log.error("No one found for "+ city);
        return null;
    }

    @Override
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName){
        Optional<MedicalRecord> medicalRecord = jsonData.getMedicalrecords().stream()
                .filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName))
                .findAny();

        if (medicalRecord.isPresent()){
            log.info("Matching medical record found");
            return medicalRecord.get();
        }
        log.error("Medical record not found!");
        return null;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecordsByFirstNameAndLastName(String firstName, String lastName){
        List<MedicalRecord> medicalRecords = jsonData.getMedicalrecords().stream()
                .filter(mr -> firstName.equals(mr.getFirstName()) && lastName.equals(mr.getLastName()))
                .collect(Collectors.toList());
        if(!medicalRecords.isEmpty()){
            log.info("Matching medical record(s) found");
            return medicalRecords;
        }
        log.error("Medical record not found!");
        return null;
    }

}
