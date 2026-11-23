package com.napier.sem;

/**
 * Stores name and population of a city
 */
public class City {
    public String name;
    public int population;

    /**
     * Default constructor, adds name and population fields
     */
    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }
    /**
     * Outputs as: 'name, population'
     */
    @Override
    public String toString() {
        return (name + ", " + population);
    }

}
