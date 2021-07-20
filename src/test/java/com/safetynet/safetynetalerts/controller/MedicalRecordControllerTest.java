package com.safetynet.safetynetalerts.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@SpringBootTest
public class MedicalRecordControllerTest {

    @InjectMocks
    MedicalRecordController medicalRecordController;
    @Mock
    MedicalRecordService medicalRecordService;

    @Test
    public void testAddMedicalRecord_Ok() {
        MedicalRecord medicalRecord = new MedicalRecord("Ran", "Tan", "01/01/2000",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);

        ResponseEntity<MedicalRecord> responseEntity = medicalRecordController.addMedicalRecord(medicalRecord);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(medicalRecordService).addMedicalRecord(medicalRecord);
    }

    @Test
    public void testUpdateMedicalRecord_NotFound() {
        MedicalRecord medicalRecord = new MedicalRecord("Ran", "Tan", "01/01/2000",
                Arrays.asList("test:50mg", "test2:1000mg"), Arrays.asList("butter", "rice"));
        when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class))).thenReturn(null);

        ResponseEntity<MedicalRecord> responseEntity = medicalRecordController.updateMedicalRecord(medicalRecord);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDeleteMedicalRecord_NotFound() {
        when(medicalRecordService.deleteMedicalRecord(anyString(), anyString())).thenReturn(false);

        ResponseEntity<String> responseEntity = medicalRecordController.deleteMedicalRecord("random", "random");

        assertNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}