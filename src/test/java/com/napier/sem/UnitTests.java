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

    @Test
    void testTopPopulatedCountries() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("China", "India");

        when(rs.getInt("Population"))
                .thenReturn(1411750000, 1380000000);

        // Run the method being tested
        a.topPopulatedCountries(2);

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

        // Check that the SQL query limits the results
        verify(stmt).executeQuery(
                contains("LIMIT 2"));
    }

    @Test
    void testTopCountriesContinent() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("China", "India");

        when(rs.getInt("Population"))
                .thenReturn(1411750000, 1380000000);

        // Run the method being tested
        a.topCountriesContinent("Asia", 2);

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

        // Check that the SQL query limits the results
        verify(stmt).executeQuery(
                contains("LIMIT 2"));
    }

    @Test
    void testTopCountriesRegion() throws Exception
    {
        // Simulate two countries being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("Italy", "Spain");

        when(rs.getInt("Population"))
                .thenReturn(58870762, 47450795);

        // Run the method being tested
        a.topCountriesRegion("Southern Europe", 2);

        // Expected console output
        String expected =
                "Italy - 58870762\n" +
                        "Spain - 47450795";

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

        // Check that the SQL query limits the results
        verify(stmt).executeQuery(
                contains("LIMIT 2"));
    }

    @Test
    void testGetCitiesRegion() throws Exception
    {
        // Simulate two cities being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("London", "Dublin");

        when(rs.getInt("Population"))
                .thenReturn(7285000, 481854);

        // Run the method being tested
        a.getCitiesRegion("British Islands");

        // Expected console output
        String expected =
                "London - 7285000\n" +
                        "Dublin - 481854";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query joins the country table
        verify(stmt).executeQuery(
                contains("JOIN country ON city.CountryCode = country.Code"));

        // Check that the SQL query filters by region
        verify(stmt).executeQuery(
                contains("WHERE country.Region = 'British Islands'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY city.Population DESC"));
    }

    @Test
    void testGetCitiesDistrict() throws Exception
    {
        // Simulate two cities being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("Glasgow", "Edinburgh");

        when(rs.getInt("Population"))
                .thenReturn(619680, 450180);

        // Run the method being tested
        a.getCitiesDistrict("Scotland");

        // Expected console output
        String expected =
                "Glasgow - 619680\n" +
                        "Edinburgh - 450180";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query filters by district
        verify(stmt).executeQuery(
                contains("WHERE District = 'Scotland'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY Population DESC"));
    }

    @Test
    void testTopCitiesCountry() throws Exception
    {
        // Simulate two cities being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("London", "Birmingham");

        when(rs.getInt("Population"))
                .thenReturn(7285000, 1013000);

        // Run the method being tested
        a.topCitiesCountry("United Kingdom", 2);

        // Expected console output
        String expected =
                "London - 7285000\n" +
                        "Birmingham - 1013000";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query joins the country table
        verify(stmt).executeQuery(
                contains("JOIN country ON city.CountryCode = country.Code"));

        // Check that the SQL query filters by country
        verify(stmt).executeQuery(
                contains("WHERE country.Name = 'United Kingdom'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY city.Population DESC"));

        // Check that the SQL query limits the results
        verify(stmt).executeQuery(
                contains("LIMIT 2"));
    }

    @Test
    void testTopCitiesDistrict() throws Exception
    {
        // Simulate two cities being returned by the database
        when(rs.next()).thenReturn(true, true, false);

        when(rs.getString("Name"))
                .thenReturn("Glasgow", "Edinburgh");

        when(rs.getInt("Population"))
                .thenReturn(619680, 450180);

        // Run the method being tested
        a.topCitiesDistrict("Scotland", 2);

        // Expected console output
        String expected =
                "Glasgow - 619680\n" +
                        "Edinburgh - 450180";

        // Capture actual output
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        // Check that the output is correct
        assertEquals(expected, result);

        // Check that the SQL query filters by district
        verify(stmt).executeQuery(
                contains("WHERE District = 'Scotland'"));

        // Check that the SQL query sorts population descending
        verify(stmt).executeQuery(
                contains("ORDER BY Population DESC"));

        // Check that the SQL query limits the results
        verify(stmt).executeQuery(
                contains("LIMIT 2"));
    }


}