package com.napier.sem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UnitTests {
    static Main main;

    @BeforeAll
    static void init() {
        main = new Main();
    }
    //ADD NEW TESTS HERE


    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() throws Exception {
        con = mock(Connection.class);
        stmt = mock(Statement.class);
        rs = mock(ResultSet.class);

        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(rs);

        main = new Main(con);

        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testTopPopulatedCapitals() throws Exception {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Seoul", "Jakarta");
        when(rs.getInt("Population")).thenReturn(9981619, 9604900);

        main.topPopulatedCapitals("Asia", 2);

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
    void testTopCapitalsRegion() throws Exception {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Madrid", "Rome");
        when(rs.getInt("Population")).thenReturn(3233527, 2860009);

        main.topCapitalsRegion("Southern Europe", 2);

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
    void testTopPopulatedCapitalsWorld() throws Exception {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getString("CapitalCity")).thenReturn("Seoul", "Jakarta");
        when(rs.getInt("Population")).thenReturn(9981619, 9604900);

        main.topPopulatedCapitalsWorld(2);

        String expected = "Seoul - 9981619\n" +
                "Jakarta - 9604900";
        String result = output.toString()
                .replace("\r\n", "\n")
                .trim();

        assertEquals(expected, result);

        verify(stmt).executeQuery(contains("ORDER BY city.Population DESC"));
        verify(stmt).executeQuery(contains("LIMIT 2"));
    }
}