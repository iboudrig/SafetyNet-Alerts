package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.dto.FireStationDTO;
import com.safetynet.safetynetalerts.model.FireStation;

@SpringBootTest
public class FirestationServiceTest {

    @Autowired
    FirestationService firestationService;

    private List<FireStation> fireStations;

    @BeforeEach
    private void setUp(){
        FireStation fireStation1 = new FireStation("12 street", 1);
        FireStation fireStation2 = new FireStation("5 wall combo", 2);
        FireStation fireStation3 = new FireStation("29 15th St", 2);
        fireStations = new ArrayList<>();
        fireStations.add(fireStation1);
        fireStations.add(fireStation2);
        fireStations.add(fireStation3);

        firestationService.setFireStationList(fireStations);

    }

    @Test
    public void testList(){
        Iterable<FireStation> results = firestationService.list();

        assertThat(results).hasSize(3).extracting(FireStation::getAddress).contains("5 wall combo");
    }

    @Test
    public void testAddFirestation_success(){
        FireStation fireStation = new FireStation("834 Binoc Ave", 3);
        FireStation result = firestationService.addFirestation(fireStation);

        assertNotNull(result);
        assertThat(fireStations).hasSize(4).extracting(FireStation::getStation).containsExactly(1, 2, 2, 3);
    }

    @Test
    public void testAddFirestation_nullAddress(){
        FireStation fireStation = new FireStation();
        FireStation result = firestationService.addFirestation(fireStation);

        assertNull(result);
        assertThat(fireStations).hasSize(3);
    }

    @Test
    public void testAddFirestation_emptyAddress(){
        FireStation fireStation = new FireStation("", 9);
        FireStation result = firestationService.addFirestation(fireStation);

        assertNull(result);
        assertThat(fireStations).hasSize(3);
    }

    @Test
    public void testUpdateFirestation_success(){
        FireStation fireStation = new FireStation("5 wall combo", 4);
        FireStation result = firestationService.updateFirestation(fireStation);

        assertThat(fireStations).hasSize(3).extracting(FireStation::getStation).contains(4);
    }

    @Test
    public void testUpdateFirestation_fail(){
        FireStation fireStation = new FireStation("3 street combo", 4);
        FireStation result = firestationService.updateFirestation(fireStation);

        assertNull(result);
        assertThat(fireStations).hasSize(3).extracting(FireStation::getStation).doesNotContain(4);
    }

    @Test
    public void testDeleteFirestation_success(){
        String address = "29 15th St";
        firestationService.deleteFirestation(address);

        assertThat(fireStations).hasSize(2).extracting(FireStation::getAddress).doesNotContain("29 15th St");
    }

    @Test
    public void testDeleteFirestation_fail(){
        String address = "29 Random";
        firestationService.deleteFirestation(address);

        assertThat(fireStations).hasSize(3);

    }

    @Test
    public void testGetPersonsCoveredByFirestation_success(){
        int stationNumber = 2;
        FireStationDTO fireStationDTO = firestationService.getPersonsCoveredByFirestation(stationNumber);

        assertNotNull(fireStationDTO);
    }

    @Test
    public void testGetPersonsCoveredByFirestation_fail(){
        int stationNumber = 6;
        FireStationDTO fireStationDTO = firestationService.getPersonsCoveredByFirestation(stationNumber);

        assertNull(fireStationDTO);
    }

}
