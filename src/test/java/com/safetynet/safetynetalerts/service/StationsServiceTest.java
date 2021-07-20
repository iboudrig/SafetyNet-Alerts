package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.dto.StationsDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class StationsServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    StationsService stationsService;

    @Test
    public void testGetFamiliesCovered_success(){
        FireStation fireStation = new FireStation("12 street", 1);
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));
        when(dataService.getAllFirestationsByStationNumber(anyList())).thenReturn(Collections.singletonList(fireStation));
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(Collections.singletonList(person));
        when(dataService.getMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(medicalRecord);
        Iterable<StationsDTO> families = stationsService.getFamiliesCovered(Arrays.asList(1, 2));

        assertThat(families).isNotNull();
        assertThat(families).extracting(StationsDTO::getAddress).containsExactly("12 street");
    }

    @Test
    public void testGetFamiliesCovered_noMatchingFirestation(){
        when(dataService.getAllFirestationsByStationNumber(anyList())).thenReturn(List.of());
        Iterable<StationsDTO> families = stationsService.getFamiliesCovered(Arrays.asList(1, 2));

        assertThat(families).isNull();
    }
    @Test
    public void testGetFamiliesCovered_uninhabitedArea(){
        FireStation fireStation = new FireStation("12 street", 1);
        when(dataService.getAllFirestationsByStationNumber(anyList())).thenReturn(Collections.singletonList(fireStation));
        when(dataService.getAllPersonsByAddress(anyString())).thenReturn(null);
        Iterable<StationsDTO> families = stationsService.getFamiliesCovered(Arrays.asList(1, 2));

        assertThat(families).isNull();
    }
}
