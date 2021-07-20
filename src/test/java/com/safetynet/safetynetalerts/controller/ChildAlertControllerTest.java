package com.safetynet.safetynetalerts.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.safetynetalerts.dto.ChildAlertDTO;
import com.safetynet.safetynetalerts.dto.ChildDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.ChildAlertService;

@SpringBootTest
public class ChildAlertControllerTest {

    @InjectMocks
    ChildAlertController childAlertController;
    @Mock
    ChildAlertService childAlertService;

    @Test
    public void testGetChildrenByAddress_Ok() {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO(Collections.singletonList(new ChildDTO("Master", "Rin", 17)),
                                        Collections.singletonList(new Person()));
        when(childAlertService.getChildrenByAddress(anyString())).thenReturn(childAlertDTO);

        ResponseEntity<ChildAlertDTO> response = childAlertController.getChildrenByAddress("Random street");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(childAlertService).getChildrenByAddress("Random street");
    }

    @Test
    public void testGetChildrenByAddress_NotFound() {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO(List.of(), List.of());
        when(childAlertService.getChildrenByAddress(anyString())).thenReturn(childAlertDTO);

        ResponseEntity<ChildAlertDTO> response = childAlertController.getChildrenByAddress("Random street");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
