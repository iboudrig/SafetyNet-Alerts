package com.safetynet.safetynetalerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireDTO {
    private List<ResidentDTO> residents;
    private int station;
}
