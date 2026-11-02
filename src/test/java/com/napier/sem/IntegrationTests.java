package com.napier.sem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

    private Main main;
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        main = new Main();
        main.connect("localhost:3306", 30000);

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        main.disconnect();
    }

    @Test
    void testTopPopulatedCapitals() {
        main.topPopulatedCapitals("Asia", 5);

        String result = output.toString().trim();

        assertTrue(result.contains("Seoul - 9981619"));
        assertTrue(result.contains("Jakarta - 9604900"));
        assertEquals(5, result.lines().count());
    }

    @Test
    void testTopCapitalsRegion() {
        main.topCapitalsRegion("Southern Europe", 5);

        String result = output.toString().trim();

        assertFalse(result.isBlank());
        assertTrue(result.contains("Madrid"));
        assertTrue(result.contains("Roma"));
        assertEquals(5, result.lines().count());
    }

    @Test
    void testTopPopulatedCapitalsWorld() {
        main.topPopulatedCapitalsWorld(5);

        String result = output.toString().trim();

        assertFalse(result.isBlank());
        assertTrue(result.contains("Seoul"));
        assertTrue(result.contains("Jakarta"));
        assertEquals(5, result.lines().count());
    }
}