package com.napier.sem;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class IntegrationTests
{
    static Main a;

    @BeforeAll
    static void init()
    {
        a = new Main();
        a.connect("localhost:33060", 30000);
    }
    //ADD NEW TESTS HERE
    @Test
    void testTask7CorrectFirstOutput() {
        ArrayList<City> cities = a.citiesInWorldLargeToSmall();
        assertEquals("Mumbai (Bombay)", cities.get(0).name);
    }
    @Test
    void testTask7CorrectSize() {
        assertEquals(4079, a.citiesInWorldLargeToSmall().size());
    }
    @Test
    void testTask8CorrectFirstOutput() {
        ArrayList<City> cities = a.citiesInContinentLargeToSmall("Europe");
        assertEquals("Moscow", cities.get(0).name);
    }
    @Test
    void testTask8CorrectSize() {
        assertEquals(841, a.citiesInContinentLargeToSmall("Europe").size());
    }

}
