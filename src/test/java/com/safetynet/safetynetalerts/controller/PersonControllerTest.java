package com.safetynet.safetynetalerts.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

@SpringBootTest
public class PersonControllerTest {

    @InjectMocks
    PersonController personController;
    @Mock
    PersonService personService;

    @Test
    public void testList() {
        Person person = new Person("Ran", "Tan", "wall street", "Cergy",
                "95000", "01010103", "tan@gmail.com");
        when(personService.list()).thenReturn(Collections.singletonList(person));

        ResponseEntity<List<Person>> response = personController.list();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0)).isEqualTo(person);
    }

    @Test
    public void testAddPerson() {
        Person person = new Person("Ran", "Tan", "wall street",
                "Cergy", "95000", "01010103", "tan@gmail.com");
        when(personService.addPerson(any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personController.addPerson(person);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getFirstName()).isEqualTo("Ran");
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person("John", "Boyd", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        when(personService.updatePerson(any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personController.updatePerson(person);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(personService).updatePerson(person);

    }

    @Test
    public void testDeletePerson() {
        when(personService.deletePerson(anyString(), anyString())).thenReturn(true);

        ResponseEntity<String> responseEntity = personController.deletePerson("Random", "Random");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertNull(responseEntity.getBody());
        verify(personService).deletePerson("Random", "Random");
    }
}
