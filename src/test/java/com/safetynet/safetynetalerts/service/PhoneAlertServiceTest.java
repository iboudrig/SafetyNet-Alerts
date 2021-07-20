package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PhoneAlertServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    PhoneAlertService phoneAlertService;

    @Test
    public void testGetPhones_success(){
        FireStation fireStation = new FireStation("12 street", 1);
        Person person = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        when(dataService.getAllFirestationsByStationNumber(anyInt())).thenReturn(Collections.singletonList(fireStation));
        when(dataService.getAllPersonsByAddress(anyList())).thenReturn(Collections.singletonList(person));
        Iterable<String> phones = phoneAlertService.getPhones(1);

        assertThat(phones).isNotNull();
        assertThat(phones).contains("01010101");
    }

    @Test
    public void testGetPhones_unknownFirestation(){
        when(dataService.getAllFirestationsByStationNumber(anyInt())).thenReturn(List.of());
        Iterable<String> phones = phoneAlertService.getPhones(1);

        assertThat(phones).isNull();
    }

    @Test
    public void testGetPhones_uninhabitedArea(){
        FireStation fireStation = new FireStation("12 street", 1);
        when(dataService.getAllFirestationsByStationNumber(anyInt())).thenReturn(Collections.singletonList(fireStation));
        when(dataService.getAllPersonsByAddress(anyList())).thenReturn(null);
        Iterable<String> phones = phoneAlertService.getPhones(1);

        assertThat(phones).isNull();
    }
}
