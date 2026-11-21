package com.napier.sem;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UnitTests
{
    static Main a;

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeAll
    static void init()
    {
        a = new Main();
    }

    @BeforeEach
    void setUp() throws Exception
    {
        con = mock(Connection.class);
        stmt = mock(Statement.class);
        rs = mock(ResultSet.class);

        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        a = new Main(con);

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown()
    {
        System.setOut(originalOut);
    }

    @Test
    void testTopPopulatedCapitals() throws Exception
    {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Seoul", "Jakarta");
        when(rs.getInt("Population")).thenReturn(9981619, 9604900);

        a.topPopulatedCapitals("Asia", 2);

        String expected = "Seoul - 9981619\n" +
                "Jakarta - 9604900";
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        assertEquals(expected, result);

        verify(stmt).executeQuery(contains("WHERE country.Continent = 'Asia'"));
        verify(stmt).executeQuery(contains("LIMIT 2"));
    }

    @Test
    void testTopCapitalsRegion() throws Exception
    {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Madrid", "Rome");
        when(rs.getInt("Population")).thenReturn(3233527, 2860009);

        a.topCapitalsRegion("Southern Europe", 2);

        String expected = "Madrid - 3233527\n" +
                "Rome - 2860009";

        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        assertEquals(expected, result);


        verify(stmt).executeQuery(contains("WHERE country.Region = 'Southern Europe'"));
        verify(stmt).executeQuery(contains("LIMIT 2"));
    }

    @Test
    void testTopPopulatedCapitalsWorld() throws Exception
    {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Seoul", "Jakarta");
        when(rs.getInt("Population")).thenReturn(9981619, 9604900);

        a.topPopulatedCapitalsWorld(2);

        String expected = "Seoul - 9981619\n" +
                "Jakarta - 9604900";
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        assertEquals(expected, result);

        verify(stmt).executeQuery(contains("ORDER BY city.Population DESC"));
        verify(stmt).executeQuery(contains("LIMIT 2"));
    }
    @Test
    void task7NullOnFail() {
        //No connection so fails
        Main noConnection = new Main();
        assertNull(noConnection.citiesInWorldLargeToSmall());
    }
    @Test
    void task8NullOnFail() {
        //No connection so fails
        Main noConnection = new Main();
        assertNull(noConnection.citiesInContinentLargeToSmall("Europe"));
    }

    @Test
    void testGetCountriesWorld() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("China", "India");

        when(rs.getInt("Population"))
                .thenReturn(1411750000, 1380000000);

        // Run the method being tested
        a.getCountriesWorld();

        // Expected console output
        String expected =
                "China - 1411750000\n" +
                        "India - 1380000000";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY Population DESC"));
    }

    @Test
    void testGetCountriesContinent() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("China", "India");

        when(rs.getInt("Population"))
                .thenReturn(1411750000, 1380000000);

        // Run the method being tested
        a.getCountriesContinent("Asia");

        // Expected console output
        String expected =
                "China - 1411750000\n" +
                        "India - 1380000000";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query filters by continent
        verify(stmt).executeQuery(
                contains("WHERE Continent = 'Asia'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY Population DESC"));
    }

    @Test
    void testGetCountriesRegion() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("Spain", "Italy");

        when(rs.getInt("Population"))
                .thenReturn(47450795, 58870762);

        // Run the method being tested
        a.getCountriesRegion("Southern Europe");

        // Expected console output
        String expected =
                "Spain - 47450795\n" +
                        "Italy - 58870762";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query filters by region
        verify(stmt).executeQuery(
                contains("WHERE Region = 'Southern Europe'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY Population DESC"));
    }



}