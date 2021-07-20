package com.safetynet.safetynetalerts.controller;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FirestationService;

@SpringBootTest
public class FirestationControllerTest {

    @InjectMocks
    FirestationController firestationController;
    @Mock
    FirestationService firestationService;

    private final FireStation fireStation = new FireStation("random", 3);

    @Test
    public void testAddFirestation_Ok() {
        when(firestationService.addFirestation(any(FireStation.class))).thenReturn(fireStation);

        ResponseEntity<FireStation> responseEntity = firestationController.addFirestation(fireStation);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testAddFirestation_BadRequest() {
        when(firestationService.addFirestation(any(FireStation.class))).thenReturn(null);

        ResponseEntity<FireStation> responseEntity = firestationController.addFirestation(fireStation);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testUpdateFirestation_Ok() {
        when(firestationService.updateFirestation(any(FireStation.class))).thenReturn(fireStation);

        ResponseEntity<FireStation> responseEntity = firestationController.updateFirestation(fireStation);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteFirestation_Ok() {
        when(firestationService.deleteFirestation(anyString())).thenReturn(true);

        ResponseEntity<String> responseEntity = firestationController.deleteFirestation("random street");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetPersonsCoveredByFirestation_Ok() {
        when(firestationService.getPersonsCoveredByFirestation(anyInt())).thenReturn(new FireStationDTO());

        ResponseEntity<FireStationDTO> responseEntity = firestationController.getPersonsCoveredByFirestation(3);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetPersonsCoveredByFirestation_NotFound() {
        when(firestationService.getPersonsCoveredByFirestation(anyInt())).thenReturn(null);

        ResponseEntity<FireStationDTO> responseEntity = firestationController.getPersonsCoveredByFirestation(3);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
