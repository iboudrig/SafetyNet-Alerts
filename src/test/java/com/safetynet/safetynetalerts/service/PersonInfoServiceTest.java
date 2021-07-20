package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.dto.PersonInfoDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PersonInfoServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    PersonInfoService personInfoService;

    @Test
    public void testGetPersonInfo_success(){
        Person person1 = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        Person person2 = new Person("Yan", "Fan", "13 street",
                "Massy", "91300", "01010102", "lan@gmail.com");
        MedicalRecord medicalRecord1 = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Yan", "Fan", "01/01/2012",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("pasta", "sugar"));
        when(dataService.getPersonsByFirstNameAndLastName(anyString(), anyString())).thenReturn(Arrays.asList(person1, person2));
        when(dataService.getAllMedicalRecordsByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(Arrays.asList(medicalRecord1, medicalRecord2));

        Iterable<PersonInfoDTO> infos = personInfoService.getPersonInfo("Yan", "Fan");

        assertThat(infos).hasSize(2).extracting(PersonInfoDTO::getAddress).containsExactly("12 street", "13 street");
    }

    @Test
    public void testGetPersonInfo_noMatch(){
        when(dataService.getPersonsByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);
        when(dataService.getAllMedicalRecordsByFirstNameAndLastName(anyString(), anyString())).thenReturn(null);
        Iterable<PersonInfoDTO> infos = personInfoService.getPersonInfo("Random", "Random");

        assertNull(infos);
    }
}
