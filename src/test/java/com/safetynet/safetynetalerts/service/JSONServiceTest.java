package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.JsonData;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class JSONServiceTest {

    @Autowired
    private JSONService jsonService;

    @Test
    void testGetDataFromJSONFile_Ok(){
        JsonData jsonData = jsonService.getDataFromJSONFile();
        assertNotNull(jsonData);
        assertEquals("John",jsonData.getPersons().get(0).getFirstName());
    }

    @Test
    public void testGetFirestationByStationNumber_success(){
        int stationNumber = 2;
        List<FireStation> fireStationList = jsonService.getAllFirestationsByStationNumber(stationNumber);

        assertThat(fireStationList).hasSize(3);
    }

    @Test
    public void testGetFirestationByStationNumber_fail(){
        int stationNumber = 6;
        List<FireStation> fireStationList = jsonService.getAllFirestationsByStationNumber(stationNumber);

        assertThat(fireStationList).isEmpty();
    }

    @Test
    public void testGetFirestationByStationNumber_list_success(){
      List<Integer> stations = Arrays.asList(1, 2);
      List<FireStation> fireStationList = jsonService.getAllFirestationsByStationNumber(stations);

      assertThat(fireStationList).hasSize(6);
    }

    @Test
    public void testGetFirestationByStationNumber_list_fail(){
        List<Integer> stations = Arrays.asList(5, 7);
        List<FireStation> fireStationList = jsonService.getAllFirestationsByStationNumber(stations);

        assertThat(fireStationList).isEmpty();
    }

    @Test
    public void testGetAllPersonsByAddress_success(){
        List<String> addresses = Arrays.asList("892 Downing Ct", "29 15th St", "951 LoneTree Rd");
        List<Person> personList = jsonService.getAllPersonsByAddress(addresses);

        assertThat(personList).hasSize(5).extracting(Person::getFirstName)
                .contains("Jonanathan", "Sophia", "Warren", "Zach", "Eric");
    }

    @Test
    public void testGetAllPersonsByAddress_fail(){
        List<String> addresses = Arrays.asList("123 Downing Ct", "12 wall street");
        List<Person> personList = jsonService.getAllPersonsByAddress(addresses);

        assertNull(personList);
    }

    @Test
    public void testGetAllPersonsByAddress_singleAddress_success(){
        List<Person> personList = jsonService.getAllPersonsByAddress("892 Downing Ct");

        assertThat(personList).hasSize(3).extracting(Person::getFirstName)
                .containsExactly("Sophia", "Warren", "Zach");
    }

    @Test
    public void testGetAllPersonsByAddress_singleAddress_fail(){
        List<Person> personList = jsonService.getAllPersonsByAddress("Random address");

        assertNull(personList);
    }

    @Test
    public void testGetPersonByFirstNameAndLastName_success(){
        List<Person> personList = jsonService.getPersonsByFirstNameAndLastName("Sophia", "Zemicks");

        //Even though the case wasn't asserted here, the method is supposed to return all persons matching firstName and lastName
        assertThat(personList).hasSize(1).extracting(Person::getFirstName)
                .containsExactly("Sophia");
    }

    @Test
    public void testGetPersonByFirstNameAndLastName_fail(){
        List<Person> personList = jsonService.getPersonsByFirstNameAndLastName("Random", "Random");

        assertNull(personList);
    }

    @Test
    public void testGetPersonsByCity_success(){
        List<Person> personList = jsonService.getPersonsByCity("Culver");

        assertThat(personList).hasSize(23);
    }

    @Test
    public void testGetPersonsByCity_fail(){
        List<Person> personList = jsonService.getPersonsByCity("Random city");

        assertNull(personList);
    }

    @Test
    public void testGetMedicalRecordByFirstNameAndLastName_success(){
        String firstName = "Sophia";
        String lastName = "Zemicks";
        MedicalRecord medicalRecord = jsonService.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

        assertEquals("03/06/1988", medicalRecord.getBirthdate());
    }

    @Test
    public void testGetMedicalRecordByFirstNameAndLastName_fail(){
        String firstName = "Soleil";
        String lastName = "Zemicks";
        MedicalRecord medicalRecord = jsonService.getMedicalRecordByFirstNameAndLastName(firstName, lastName);

        assertNull(medicalRecord);
    }

    @Test
    public void testGetAllMedicalRecordsByFirstNameAndLastName_success(){
        List<MedicalRecord> medicalRecords = jsonService.getAllMedicalRecordsByFirstNameAndLastName("John", "Boyd");

        assertThat(medicalRecords).extracting(MedicalRecord::getBirthdate).contains("03/06/1984");
    }

    @Test
    public void testGetAllMedicalRecordsByFirstNameAndLastName_fail(){
        List<MedicalRecord> medicalRecords = jsonService.getAllMedicalRecordsByFirstNameAndLastName("Random", "Random");

        assertNull(medicalRecords);
    }

        @Test
    public void testGetFirestationByAddress_success(){
        FireStation fireStation = jsonService.getFirestationByAddress("892 Downing Ct");

        assertEquals(2, fireStation.getStation());
    }
    @Test
    public void testGetFirestationByAddress_fail(){
        FireStation fireStation = jsonService.getFirestationByAddress("Random address");

        assertNull(fireStation);
    }
}
