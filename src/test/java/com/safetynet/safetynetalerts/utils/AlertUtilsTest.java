package com.safetynet.safetynetalerts.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlertUtilsTest {

    @Test
    public void testCalculateAge(){
        int age = AlertUtils.calculateAge("01/06/2000");

        assertEquals(21, age);
    }
}
