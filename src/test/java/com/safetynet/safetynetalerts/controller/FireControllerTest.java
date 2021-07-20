package com.safetynet.safetynetalerts.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalerts.dto.FireDTO;
import com.safetynet.safetynetalerts.dto.ResidentDTO;
import com.safetynet.safetynetalerts.service.FireService;

@SpringBootTest
public class FireControllerTest {

    @InjectMocks
    FireController fireController;
    @Mock
    FireService fireService;

    @Test
    public void testGetAllPersonAndStationByAddress_Ok() {
        ResidentDTO resident = new ResidentDTO("Ran", "Tan", "01012000", 45,
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        when(fireService.getAllPersonAndStationByAddress(anyString())).thenReturn(new FireDTO(Collections.singletonList(resident), 3));

        ResponseEntity<FireDTO> responseEntity = fireController.getAllPersonAndStationByAddress("random");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetAllPersonAndStationByAddress_NotFound() {
        when(fireService.getAllPersonAndStationByAddress(anyString())).thenReturn(new FireDTO(List.of(), 0));

        ResponseEntity<FireDTO> responseEntity = fireController.getAllPersonAndStationByAddress("random");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
