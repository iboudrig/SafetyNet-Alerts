package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class ChildAlertServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    ChildAlertService childAlertService;

    @Test
    public void testGetChildrenByAddress_success(){
        List<Person> persons = new ArrayList<>();
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        persons.add(person);
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/2012",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));

        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(persons);
        when(dataService.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(medicalRecord);

        ChildAlertDTO childAlertDTO = childAlertService.getChildrenByAddress("Random street");

        assertThat(childAlertDTO.getChildren()).extracting(ChildDTO::getFirstName).contains("Yan");

    }

    @Test
    public void testGetChildrenByAddress_wrongAddress(){
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(null);
        ChildAlertDTO childAlertDTO = childAlertService.getChildrenByAddress("Random street");

        assertThat(childAlertDTO.getChildren()).isEmpty();
        assertThat(childAlertDTO.getFamilyMembers()).isEmpty();
    }

    @Test
    public void testGetChildrenByAddress_noChild(){
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));

        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(Collections.singletonList(person));
        when(dataService.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(medicalRecord);

        ChildAlertDTO childAlertDTO = childAlertService.getChildrenByAddress("Random street");

        assertThat(childAlertDTO.getChildren()).isEmpty();
        assertThat(childAlertDTO.getFamilyMembers()).isEmpty();
    }
}
