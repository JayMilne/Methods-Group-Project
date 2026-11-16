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

}