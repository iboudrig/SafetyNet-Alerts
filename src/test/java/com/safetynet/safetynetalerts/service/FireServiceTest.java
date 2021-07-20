package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.ResidentDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class FireServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    FireService fireService;

    @Test
    public void testGetAllPersonAndStationByAddress_success(){
        List<Person> persons = new ArrayList<>();
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        persons.add(person);
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));
        FireStation fireStation = new FireStation("12 street", 1);
        when(dataService.getFirestationByAddress(anyString())).thenReturn(fireStation);
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(persons);
        when(dataService.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(medicalRecord);

        FireDTO fireDTO = fireService.getAllPersonAndStationByAddress("Random address");

        assertThat(fireDTO.getResidents()).extracting(ResidentDTO::getFirstName).containsExactly("Yan");
        assertEquals(1, fireDTO.getStation());
    }

    @Test
    public void testGetAllPersonAndStationByAddress_unknownAddress(){
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(null);
        FireDTO fireDTO = fireService.getAllPersonAndStationByAddress("Random address");

        assertThat(fireDTO.getResidents()).isEmpty();
    }

    @Test
    public void testGetAllPersonAndStationByAddress_noStation(){
        List<Person> persons = new ArrayList<>();
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        persons.add(person);
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(persons);
        when(dataService.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(medicalRecord);
        when(dataService.getFirestationByAddress(anyString())).thenReturn(null);

        FireDTO fireDTO = fireService.getAllPersonAndStationByAddress("Random address");

        assertEquals(0, fireDTO.getStation());
    }
}
