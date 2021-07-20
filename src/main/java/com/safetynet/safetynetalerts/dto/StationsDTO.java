package com.safetynet.safetynetalerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationsDTO {
    private String address;
    private List<ResidentDTO> residents;
}
