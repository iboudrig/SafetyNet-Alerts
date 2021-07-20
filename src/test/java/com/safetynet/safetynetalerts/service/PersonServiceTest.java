package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    private List<Person> persons = new ArrayList<>();

    @BeforeEach
    private void setUp(){
        Person person1 = new Person("Yan", "Fan", "12 street",
                "Paris", "75015", "01010101", "yan@gmail.com");
        Person person2 = new Person("Lan", "Han", "13 street",
                "Massy", "91300", "01010102", "lan@gmail.com");
        persons.add(person1);
        persons.add(person2);

        personService.setPersonList(persons);
    }

    @Test
    public void testAddPerson_success(){
        Person person3 = new Person("Ran", "Tan", "wall street",
                "Cergy", "95000", "01010103", "tan@gmail.com");
        Person result = personService.addPerson(person3);

        assertEquals("tan@gmail.com", result.getEmail());
        assertEquals("95000", personService.getPersonList().get(2).getZip());
    }

    @Test
    public void testAddPerson_fail(){
        Person person = new Person("", "  ", "wall street",
                "Cergy", "95000", "01010103", "tan@gmail.com");
        Person result = personService.addPerson(person);

        assertNull(result);
    }

    @Test
    public void testList(){
        List<Person> result = personService.list();

       assertEquals(2, result.size());
       assertEquals("Paris", result.get(0).getCity());
    }

    @Test
    public void testUpdatePerson_success(){
        Person person = new Person("Lan", "Han", "wall street",
                "Cergy", "95000", "01010103", "tan@gmail.com");

        Person result = personService.updatePerson(person);

        assertNotNull(result);
        assertEquals("wall street", result.getAddress());
    }

    @Test
    public void testUpdatePerson_fail(){
        Person person = new Person("Ran", "Tan", "wall street",
                "Cergy", "95000", "01010103", "tan@gmail.com");

        Person result = personService.updatePerson(person);

        assertNull(result);
    }

    @Test
    public void testDeletePerson_success(){
        boolean removed = personService.deletePerson("Yan", "Fan");

        assertTrue(removed);
    }

    @Test
    public void testDeletePerson_fail(){
        personService.deletePerson("Ran", "Tan");

        assertEquals(2, personService.getPersonList().size());

    }
}
