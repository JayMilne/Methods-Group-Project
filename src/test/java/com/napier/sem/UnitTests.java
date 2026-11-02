package com.napier.sem;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTests {
    static Main a;

    @BeforeAll
    static void init() {
        a = new Main();
    }

    @Test
    void task7NullOnFail() {
        //No connection so fails
        assertNull(a.citiesInWorldLargeToSmall());
    }
    @Test
    void task8NullOnFail() {
        //No connection so fails
        assertNull(a.citiesInContinentLargeToSmall("Europe"));
    }
    //ADD NEW TESTS HERE
}