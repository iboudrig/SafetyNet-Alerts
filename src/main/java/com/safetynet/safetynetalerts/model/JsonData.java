package com.safetynet.safetynetalerts.model;

import java.util.List;

import lombok.Data;

@Data
public class JsonData {
    List<Person> persons;
    List<FireStation> firestations;
    List<MedicalRecord> medicalrecords;
}
