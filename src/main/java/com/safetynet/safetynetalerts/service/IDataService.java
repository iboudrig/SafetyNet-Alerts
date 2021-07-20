package com.safetynet.safetynetalerts.service;

import java.util.List;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

public interface IDataService {

    /**
     * Returns a list of persons
     * @return Person objects
     */
    List<Person> getAllPersons();

    /**
     * Returns a list of mappings address/station
     * @return FireStation objects
     */
    List<FireStation> getAllFirestations();

    /**
     * Returns a list of medical records
     * @return MedicalRecord objects
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * Get all mappings station/address corresponding to a station number
     * @param stationNumber an int
     * @return a list of mappings
     */
    List<FireStation> getAllFirestationsByStationNumber(int stationNumber);

    /**
     * Get all mappings station/address corresponding to a list of station numbers
     * @param stations a list of int
     * @return a list of mappings
     */
    List<FireStation> getAllFirestationsByStationNumber(List<Integer> stations);

    /**
     * Get the mapping station/address corresponding to a given address
     * @param address a string
     * @return a mapping when the address is unknown
     */
    FireStation getFirestationByAddress(String address);

    /**
     * Get all persons living at a given list of addresses
     * @param addresses a list of string
     * @return a list of persons
     */
    List<Person> getAllPersonsByAddress(List<String> addresses);

    /**
     * Get all persons living at a specific address
     * @param address a string
     * @return a list of persons
     */
    List<Person> getAllPersonsByAddress(String address);

    /**
     * Find all persons matching a first name and last name
     * @param firstName a string
     * @param lastName a string
     * @return a list of persons
     */
    List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Get all persons living in the given city
     * @param city a string
     * @return a list of persons
     */
    List<Person> getPersonsByCity(String city);

    /**
     * Get a medical record based on a first and last name
     * @param firstName a string
     * @param lastName a string
     * @return a medical record if exists
     */
    MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Get all medical records matching a first name and last name
     * @param firstName a string
     * @param lastName a string
     * @return a list of records
     */
    List<MedicalRecord> getAllMedicalRecordsByFirstNameAndLastName(String firstName, String lastName);

}
