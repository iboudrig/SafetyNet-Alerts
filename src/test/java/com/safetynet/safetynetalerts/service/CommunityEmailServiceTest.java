package com.safetynet.safetynetalerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class CommunityEmailServiceTest {

    @Mock
    IDataService dataService;
    @InjectMocks
    CommunityEmailService communityEmailService;

    @Test
    public void testGetEmailsByCity_success(){
        Person person1 = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        Person person2 = new Person("Lan", "Han", "13 street",
                "Paris", "91300", "01010102", "lan@gmail.com");
        when(dataService.getPersonsByCity(anyString())).thenReturn(Arrays.asList(person1, person2));

        Iterable<String> emails = communityEmailService.getEmailsByCity("Random city");

        assertNotNull(emails);
        assertThat(emails).containsExactly("yan@gmail.com", "lan@gmail.com");

    }

    @Test
    public void testGetEmailsByCity_fail(){
        when(dataService.getPersonsByCity(anyString())).thenReturn(null);
        Iterable<String> emails = communityEmailService.getEmailsByCity("Random city");

        assertNull(emails);
    }
}
