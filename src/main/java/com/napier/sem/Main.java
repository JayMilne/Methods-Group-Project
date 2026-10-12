package com.napier.sem;

import java.sql.*;

public class Main
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args)
    {
        // Create new Application
        Main a = new Main();

        // Connect to database
        a.connect();

        //Put task code here
        a.getCountriesContinent("Europe");

        a.getCountriesRegion("British Islands");

        a.getCountriesWorld();

        a.topPopulatedCountries(10);

        a.topCountriesContinent("Europe", 10);

        a.topCountriesRegion("British Islands", 2);

        // Disconnect from database
        a.disconnect();
    }
    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    public void getCountriesWorld()
    {
        try
        {
            Statement stmt = con.createStatement();

            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "ORDER BY Population DESC";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                System.out.println(
                        rs.getString("Name") + " - " +
                                rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void getCountriesContinent(String continent)
    {
        try
        {
            Statement stmt = con.createStatement();

            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "WHERE Continent = '" + continent + "' " +
                            "ORDER BY Population DESC";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                System.out.println(
                        rs.getString("Name") + " - " +
                                rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Task 3
     * Print all countries in a region ordered by population
     * from largest population to smallest population.
     */
    public void getCountriesRegion(String region)
    {
        try
        {
            // Create SQL statement
            Statement stmt = con.createStatement();

            // SQL query to select countries from a specific region
            // and sort them by population in descending order
            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "WHERE Region = '" + region + "' " +
                            "ORDER BY Population DESC";

            // Execute query
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through results and print each country
            while (rs.next())
            {
                System.out.println(
                        rs.getString("Name") +
                                " - " +
                                rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            // Print any error messages
            System.out.println(e.getMessage());
        }
    }

    /** Task 4
     * Print the top N populated countries in the world.
     * */
    public void topPopulatedCountries(int n)
    {
        try {

            // create our sql statement
            Statement stmt = con.createStatement();

            // make our query
            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "ORDER BY Population DESC" +
                            "LIMIT " + n;

            // execute our query
            ResultSet rs = stmt.executeQuery(sql);

            //print results
            while (rs.next())
            {
                System.out.println(rs.getString("Name") + " - " + rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Task 5
     * Print the top N populated countries in a continent
     * */
    public void topCountriesContinent(String continent, int n)
    {
        try {

            // create sql statement
            Statement stmt = con.createStatement();

            // make query
            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "WHERE Continent = " + continent +
                            "ORDER BY Population DESC " +
                            "LIMIT " + n;

            // execute query
            ResultSet rs = stmt.executeQuery(sql);

            // print results
            while (rs.next())
            {
                System.out.println(rs.getString("Name") + " - " + rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /** Task 6
     * Print the top N populated countries in a region
     * */
    public void topCountriesRegion(String region, int n)
    {
        try {

            // make sql statement
            Statement stmt = con.createStatement();

            // make query
            String sql =
                    "SELECT Name, Population " +
                            "FROM country " +
                            "WHERE Region = " + region +
                            "ORDER BY Population DESC " +
                            "LIMIT " + n;

            // execute query
            ResultSet rs = stmt.executeQuery(sql);

            // print results
            while (rs.next())
            {
                System.out.println(rs.getString("Name") + " - " + rs.getInt("Population"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}

