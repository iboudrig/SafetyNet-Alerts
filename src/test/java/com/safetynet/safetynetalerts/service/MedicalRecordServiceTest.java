package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.MedicalRecord;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Autowired
    MedicalRecordService medicalRecordService;

    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    private void setUp(){
        MedicalRecord medicalRecord1 = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("cow milk", "peanut"));
        MedicalRecord medicalRecord2 = new MedicalRecord("Lan", "Han", "01/01/2012",
                Arrays.asList("ibu:250mg", "para:1000mg"), Arrays.asList("pasta", "sugar"));
        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);

        medicalRecordService.setMedicalRecordList(medicalRecords);
    }

    @Test
    public void testAddMedicalRecord_success(){
        MedicalRecord medicalRecord = new MedicalRecord("Ran", "Tan", "01/01/2000",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        MedicalRecord record = medicalRecordService.addMedicalRecord(medicalRecord);

       assertNotNull(record);
       assertThat(medicalRecords).hasSize(3).extracting(MedicalRecord::getFirstName).contains("Ran");
    }

    @Test
    public void testAddMedicalRecord_fail(){
        MedicalRecord medicalRecord = new MedicalRecord("", "Lin", "01/01/2000",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        MedicalRecord record = medicalRecordService.addMedicalRecord(medicalRecord);

        assertNull(record);
    }

    @Test
    public void testUpdateMedicalRecord_success(){
        MedicalRecord medicalRecord = new MedicalRecord("Yan", "Fan", "01/01/1978",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("cow milk", "butter"));
        MedicalRecord record = medicalRecordService.updateMedicalRecord(medicalRecord);

        assertNotNull(record);
        assertThat(record.getAllergies()).contains("butter");
    }

    @Test
    public void testUpdateMedicalRecord_fail(){
        MedicalRecord medicalRecord = new MedicalRecord("Ran", "Tan", "01/01/2000",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        MedicalRecord record = medicalRecordService.updateMedicalRecord(medicalRecord);

        assertNull(record);
    }

    @Test
    public void testDeleteMedicalRecord_success(){
        medicalRecordService.deleteMedicalRecord("Yan", "Fan");

        assertThat(medicalRecordService.getMedicalRecordList()).extracting(MedicalRecord::getFirstName, MedicalRecord::getLastName)
                .doesNotContain(tuple("Yan", "Fan"));
    }

    @Test
    public void testDeleteMedicalRecord_fail(){
        medicalRecordService.deleteMedicalRecord("Ran", "Tan");

        assertThat(medicalRecordService.getMedicalRecordList()).hasSize(2);
    }
}
