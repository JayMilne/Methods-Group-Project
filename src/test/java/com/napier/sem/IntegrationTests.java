package com.napier.sem;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

    static Main a;
    static ByteArrayOutputStream output;
    static PrintStream originalOut;

    @BeforeAll
    static void setUp() {
        a = new Main();
        a.connect("localhost:33060", 30000);

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    @BeforeEach
    void beforeEach() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }
    @AfterEach
    void afterEach() {
        System.setOut(originalOut);
    }

    @AfterAll
    static void tearDown() {
        System.setOut(originalOut);
        a.disconnect();
    }
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
    @Test
    void testTopCapitalsRegion() {
        a.topCapitalsRegion("Southern Europe", 5);

        String result = output.toString().trim();

        assertFalse(result.isBlank());
        assertTrue(result.contains("Madrid"));
        assertTrue(result.contains("Roma"));
        assertEquals(5, result.lines().count());
    }

    @Test
    void testTopPopulatedCapitalsWorld() {
        a.topPopulatedCapitalsWorld(5);

        String result = output.toString().trim();

        assertFalse(result.isBlank());
        assertTrue(result.contains("Seoul"));
        assertTrue(result.contains("Jakarta"));
        assertEquals(5, result.lines().count());
    }
    @Test
    void testTopPopulatedCapitals() {
        a.topPopulatedCapitals("Asia", 5);

        String result = output.toString().trim();

        assertTrue(result.contains("Seoul - 9981619"));
        assertTrue(result.contains("Jakarta - 9604900"));
        assertEquals(5, result.lines().count());
    }

    @Test
    void testGetCountriesWorld()
    {
        // Run the method using the actual database
        a.getCountriesWorld();

        // Capture the output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        String[] lines = result.split("\n");

        // Check that countries were returned
        assertTrue(lines.length >= 2);

        // Check the first two countries by population
        assertTrue(lines[0].startsWith("China - "));
        assertTrue(lines[1].startsWith("India - "));

        // Check that populations are in descending order
        int previousPopulation = Integer.MAX_VALUE;

        for (String line : lines)
        {
            int population = Integer.parseInt(
                    line.substring(line.lastIndexOf(" - ") + 3));

            assertTrue(population <= previousPopulation);

            previousPopulation = population;
        }
    }

    @Test
    void testGetCountriesContinent()
    {
        // Run the method using the actual database
        a.getCountriesContinent("Asia");

        // Capture the output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        String[] lines = result.split("\n");

        // Check that countries were returned
        assertTrue(lines.length > 0);

        // Check the first country by population
        assertTrue(lines[0].startsWith("China - "));

        // Check that populations are in descending order
        int previousPopulation = Integer.MAX_VALUE;

        for (String line : lines)
        {
            int population = Integer.parseInt(
                    line.substring(line.lastIndexOf(" - ") + 3));

            assertTrue(population <= previousPopulation);

            previousPopulation = population;
        }
    }

    @Test
    void testGetCountriesRegion()
    {
        // Run the method using the actual database
        a.getCountriesRegion("Southern Europe");

        // Capture the output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        String[] lines = result.split("\n");

        // Check that countries were returned
        assertTrue(lines.length > 0);

        // Check the first country by population
        assertTrue(lines[0].startsWith("Italy - "));

        // Check that populations are in descending order
        int previousPopulation = Integer.MAX_VALUE;

        for (String line : lines)
        {
            int population = Integer.parseInt(
                    line.substring(line.lastIndexOf(" - ") + 3));

            assertTrue(population <= previousPopulation);

            previousPopulation = population;
        }
    }




}