package com.safetynet.safetynetalerts.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

import com.safetynet.safetynetalerts.service.CommunityEmailService;

@SpringBootTest
public class CommunityEmailControllerTest {

    @InjectMocks
    CommunityEmailController communityEmailController;
    @Mock
    CommunityEmailService communityEmailService;

    @Test
    public void testGetEmailsByCity_Ok(){
        when(communityEmailService.getEmailsByCity(anyString())).thenReturn(Arrays.asList("a@gmail.com", "b@yahoo.fr"));

        ResponseEntity<Iterable<String>> responseEntity = communityEmailController.getEmailsByCity("Random");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(communityEmailService).getEmailsByCity("Random");
    }

    @Test
    public void testGetEmailsByCity_NotFound(){
        when(communityEmailService.getEmailsByCity(anyString())).thenReturn(null);

        ResponseEntity<Iterable<String>> responseEntity = communityEmailController.getEmailsByCity("Random");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(communityEmailService).getEmailsByCity("Random");
    }
}

