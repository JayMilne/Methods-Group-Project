package com.napier.sem;

import org.junit.jupiter.api.*;


public class IntegrationTests
{
    static Main a;

    @BeforeAll
    static void init()
    {
        a = new Main();
        a.connect("localhost:33060", 30000);
    }
    //ADD NEW TESTS HERE
}
