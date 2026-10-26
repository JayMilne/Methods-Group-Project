package com.napier.sem;

import org.junit.jupiter.api.*;


public class IntegrationTests
{
    static Main a;

    @BeforeAll
    static void init()
    {
        a = new Main();
        a.connect();
    }

    @Test
    void printSalariesTestNull()
    {
        a.getCountriesContinent("Europe");
    }
}
